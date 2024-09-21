package com.devmike.data.sources.issues

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.devmike.data.mapper.toEntity
import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.repository.CachedIssueRepo
import com.devmike.network.networkSource.GitHubIssuesRepo
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class IssuePagedDataSources
    @Inject
    constructor(
        private val cachedIssueRepo: CachedIssueRepo,
        private val gitHubIssuesRepo: GitHubIssuesRepo,
        private val owner: String,
        private val name: String,
    ) : RemoteMediator<Int, CachedIssueEntity>() {
        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, CachedIssueEntity>,
        ): MediatorResult {
            return try {
                val cursor: String? =
                    when (loadType) {
                        LoadType.REFRESH -> null
                        LoadType.PREPEND -> return MediatorResult.Success(
                            endOfPaginationReached = true,
                        )
                        LoadType.APPEND -> {
                            val remoteKey =
                                cachedIssueRepo.remoteKeyByQuery(
                                    "$owner/$name",
                                    null,
                                    listOf(),
                                )

                            Timber.tag("issueerror").d("cursor is $remoteKey")
                            remoteKey?.nextCursor
                        }
                    }

                val response = gitHubIssuesRepo.getRepositoryIssues(owner, name, cursor)

                response.fold(
                    onFailure = { error ->

                        MediatorResult.Error(error)
                    },
                    onSuccess = { pagedDtoWrapper ->

                        cachedIssueRepo.insertIssues(
                            loadType == LoadType.REFRESH && pagedDtoWrapper.data.isNotEmpty(),
                            pagedDtoWrapper.nextCursor,
                            CachedIssueDTO(
                                repository = "$owner/$name",
                                assignee = null,
                                labels = listOf(),
                            ),
                            issues = pagedDtoWrapper.data.map { it.toEntity() },
                        )

                        MediatorResult.Success(
                            endOfPaginationReached = !pagedDtoWrapper.hasNextPage,
                        )
                    },
                )
            } catch (e: Exception) {
                MediatorResult.Error(e)
            }
        }
    }
