package com.devmike.data.sources.issues

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.devmike.data.mapper.toIssueModel
import com.devmike.database.repository.CachedIssueRepo
import com.devmike.domain.models.IssueModel
import com.devmike.network.networkSource.GitHubIssuesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class IssuesRepository
    @Inject
    constructor(
        private val gitHubIssuesRepo: GitHubIssuesRepo,
        private val cachedIssueRepo: CachedIssueRepo,
    ) {
        @OptIn(ExperimentalPagingApi::class)
        fun getPagedIssues(
            owner: String,
            name: String,
            assignee: String?,
            labels: List<String>,
        ): Flow<PagingData<IssueModel>> {
            val pagingSourceFactory =
                {
                    cachedIssueRepo.getIssueByRepository(
                        "$owner/$name",
                        assignee = assignee,
                        labels,
                    )
                }

            return Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false),
                remoteMediator =
                    IssuePagedDataSources(
                        cachedIssueRepo = cachedIssueRepo,
                        gitHubIssuesRepo = gitHubIssuesRepo,
                        owner = owner,
                        name = name,
                    ),
                pagingSourceFactory = pagingSourceFactory,
            ).flow.map { pagingData ->
                pagingData.map {
                    it.toIssueModel()
                }
            }
        }
    }
