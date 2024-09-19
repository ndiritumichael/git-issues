package com.devmike.data.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.devmike.data.mapper.toCachedRepository
import com.devmike.database.GithubDatabase
import com.devmike.database.dao.RepositorySearchDAO
import com.devmike.database.entities.CachedRepository
import com.devmike.database.entities.RemoteKeyEntity
import com.devmike.network.networkSource.GitHubIssuesRepo
import java.net.SocketTimeoutException

/**
 * return key based on load type
 */
@OptIn(ExperimentalPagingApi::class)
class RepositoriesDataSources(
    private val query: String,
    private val githubIssuesRepository: GitHubIssuesRepo,
    private val cacheRepositoryDAO: RepositorySearchDAO,
    private val db: GithubDatabase,
) : RemoteMediator<Int, CachedRepository>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CachedRepository>,
    ): MediatorResult {
        return try {
            val cursor: String? =
                when (loadType) {
                    LoadType.REFRESH -> null
                    LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                    LoadType.APPEND -> {
                        val remoteKey = cacheRepositoryDAO.remoteKeyByQuery(query)
                        remoteKey?.nextCursor
                    }
                }

            val response = githubIssuesRepository.searchRepositories(query, cursor)

            response.fold(
                onFailure = { error ->
                    MediatorResult.Error(error)
                },
                onSuccess = { result ->
                    db.withTransaction {
                        if (loadType == LoadType.REFRESH && result.data.isNotEmpty()) {
                            cacheRepositoryDAO.deleteRepositories(query)
                            cacheRepositoryDAO.clearRemoteKeysForQuery(query)
                        }
                        cacheRepositoryDAO.upsertAll(
                            result.data.map { it.toCachedRepository(query) },
                        )
                        cacheRepositoryDAO.insertOrReplace(
                            RemoteKeyEntity(searchQuery = query, nextCursor = result.nextCursor),
                        )
                    }

                    MediatorResult.Success(endOfPaginationReached = !result.hasNextPage)
                },
            )
        } catch (e: SocketTimeoutException) {
            MediatorResult.Error(e)
        }
    }
}
