package com.devmike.data.sources.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.devmike.data.mapper.toCachedRepository
import com.devmike.database.entities.CachedRepository
import com.devmike.database.repository.CachedRepoSearch
import com.devmike.network.networkSource.GitHubIssuesRepo
import java.net.SocketTimeoutException

/**
 * return key based on load type
 */
@OptIn(ExperimentalPagingApi::class)
class RepositoriesDataSources(
    private val query: String,
    private val githubIssuesRepository: GitHubIssuesRepo,
    private val cachedRepository: CachedRepoSearch,
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
                        val remoteKey = cachedRepository.remoteKeyByQuery(query)
                        remoteKey?.nextCursor
                    }
                }

            val response = githubIssuesRepository.searchRepositories(query, cursor)

            response.fold(
                onFailure = { error ->
                    MediatorResult.Error(error)
                },
                onSuccess = { result ->

                    cachedRepository.insertRepositories(
                        loadType == LoadType.REFRESH && result.data.isNotEmpty(),
                        query = query,
                        nextCursor = result.nextCursor,
                        result.data.map { it.toCachedRepository(query) },
                    )

                    MediatorResult.Success(endOfPaginationReached = !result.hasNextPage)
                },
            )
        } catch (e: SocketTimeoutException) {
            MediatorResult.Error(e)
        }
    }
}
