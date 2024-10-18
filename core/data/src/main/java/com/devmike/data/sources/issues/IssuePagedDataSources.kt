package com.devmike.data.sources.issues

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.devmike.data.mapper.toCachedIssueDTO
import com.devmike.data.mapper.toIssuesWithLabelsandAssignees
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import com.devmike.database.repository.CachedIssueRepo
import com.devmike.domain.models.IssueSearchModel
import com.devmike.network.networkSource.GitHubIssuesRepo
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class IssuePagedDataSources
    @Inject
    constructor(
        private val cachedIssueRepo: CachedIssueRepo,
        private val gitHubIssuesRepo: GitHubIssuesRepo,
        private val issueSearchModel: IssueSearchModel,
    ) : RemoteMediator<Int, IssueWithAssigneesAndLabels>() {
        override suspend fun load(
            loadType: LoadType,
            state: PagingState<Int, IssueWithAssigneesAndLabels>,
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
                                    issueSearchModel.toCachedIssueDTO(),
                                )

                            remoteKey?.nextCursor
                        }
                    }

                val response =
                    gitHubIssuesRepo.getRepositoryIssues(
                        issueSearchModel.copy(cursor = cursor),
                    )

                response.fold(
                    onFailure = { error ->

                        MediatorResult.Error(error)
                    },
                    onSuccess = { pagedDtoWrapper ->

                        cachedIssueRepo.insertIssues(
                            loadType == LoadType.REFRESH,
                            pagedDtoWrapper.nextCursor,
                            issueSearchModel
                                .copy(
                                    cursor = pagedDtoWrapper.nextCursor,
                                ).toCachedIssueDTO(),
                            issues =
                                pagedDtoWrapper.data.map {
                                    it.toIssuesWithLabelsandAssignees()
                                },
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
