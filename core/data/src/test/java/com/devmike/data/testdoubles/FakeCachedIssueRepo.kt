package com.devmike.data.testdoubles

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.CachedIssueKeyEntity
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import com.devmike.database.repository.CachedIssueRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class FakeCachedIssueRepo : CachedIssueRepo {
    private val issues = MutableStateFlow<List<IssueWithAssigneesAndLabels>>(listOf())
    private val remoteKeys =
        mutableMapOf(
            CachedIssueDTO(
                repository = "flutter/flutter",
                query = "crash",
                assignee = listOf("mary.wilson"),
                labels = listOf("bug", "crash"),
                issueStatus = "open",
            ) to
                CachedIssueKeyEntity(
                    repoName = "flutter/flutter",
                    assignee = listOf("mary.wilson"),
                    labels = listOf("bug", "crash"),
                    query = "crash",
                    issueState = "open",
                    nextCursor = "cursor1",
                ),
            CachedIssueDTO(
                repository = "flutter/flutter",
                query = "performance",
                assignee = emptyList(),
                labels = listOf("performance"),
                issueStatus = "open",
            ) to
                CachedIssueKeyEntity(
                    repoName = "flutter/flutter",
                    assignee = emptyList(),
                    labels = listOf("performance"),
                    query = "performance",
                    issueState = "open",
                    nextCursor = "cursor2",
                ),
        )

    override suspend fun insertIssues(
        isRefresh: Boolean,
        nextCursor: String?,
        issueDTO: CachedIssueDTO,
        issues: List<IssueWithAssigneesAndLabels>,
    ) {
        this.issues.update {
            if (isRefresh) {
                emptyList()
            } else
                {
                    it + issues
                }
        }
    }

    override fun getIssueByRepository(
        repository: String,
        assignee: List<String>?,
        labels: List<String>?,
        queryString: String?,
        issueState: String,
    ): PagingSource<Int, IssueWithAssigneesAndLabels> {
        return object : PagingSource<Int, IssueWithAssigneesAndLabels>() {
            override suspend fun load(
                params: LoadParams<Int>,
            ): LoadResult<Int, IssueWithAssigneesAndLabels> {
                val filteredIssues =
                    issues.value.filter {
                        it.issue.repositoryName == repository
                    }

                return LoadResult.Page(
                    data = filteredIssues,
                    prevKey = null,
                    nextKey = null,
                )
            }

            override fun getRefreshKey(state: PagingState<Int, IssueWithAssigneesAndLabels>): Int? {
                return null
            }
        }
    }

    override suspend fun remoteKeyByQuery(issueDTO: CachedIssueDTO): CachedIssueKeyEntity? {
        return remoteKeys[issueDTO]
    }
}
