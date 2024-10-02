package com.devmike.data.sources.issues

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.devmike.data.mapper.toIssueModel
import com.devmike.data.mapper.toModel
import com.devmike.database.repository.CachedIssueRepo
import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import com.devmike.domain.repository.IssuesRepository
import com.devmike.network.networkSource.GitHubIssuesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IssuesRepositoryImpl
    @Inject
    constructor(
        private val gitHubIssuesRepo: GitHubIssuesRepo,
        private val cachedIssueRepo: CachedIssueRepo,
    ) : IssuesRepository {
        override fun getRepositoryLabels(
            owner: String,
            repository: String,
        ): Flow<PagingData<LabelModel>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 20,
                    ),
            ) {
                RepoLabelsPagedSource(
                    owner = owner,
                    name = repository,
                    gitHubIssuesRepo = gitHubIssuesRepo,
                )
            }.flow

        override fun getRepositoryAssignees(
            owner: String,
            repository: String,
        ): Flow<PagingData<AssigneeModel>> =
            Pager(
                config =
                    PagingConfig(
                        pageSize = 20,
                    ),
            ) {
                RepoAssigneesPagedSource(
                    owner = owner,
                    name = repository,
                    gitHubIssuesRepo = gitHubIssuesRepo,
                )
            }.flow

        @OptIn(ExperimentalPagingApi::class)
        override fun getPagedIssues(
            issueSearchModel: IssueSearchModel,
        ): Flow<PagingData<IssueModel>> {
            val pagingSourceFactory =
                {
                    cachedIssueRepo.getIssueByRepository(
                        repository = issueSearchModel.repository,
                        assignee = issueSearchModel.assignees,
                        labels = issueSearchModel.labels,
                        queryString = issueSearchModel.query,
                        issueState = issueSearchModel.issueState,
                    )
                }

            return Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false),
                remoteMediator =
                    IssuePagedDataSources(
                        cachedIssueRepo = cachedIssueRepo,
                        gitHubIssuesRepo = gitHubIssuesRepo,
                        issueSearchModel = issueSearchModel,
                    ),
                pagingSourceFactory = pagingSourceFactory,
            ).flow.map { pagingData ->
                pagingData.map { withAssigneesAndLabels ->

                    withAssigneesAndLabels.issue.toIssueModel(
                        assignee = withAssigneesAndLabels.assignees.toSet().map { it.assignee },
                        labels = withAssigneesAndLabels.labels.toSet().map { it.toModel() },
                    )
                }
            }
        }
    }
