@file:OptIn(ExperimentalPagingApi::class)

package com.devmike.data.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator.MediatorResult
import com.devmike.data.sources.issues.IssuePagedDataSources
import com.devmike.data.testdoubles.FakeCachedIssueRepo
import com.devmike.data.testdoubles.FakeGithubRepo
import com.devmike.data.util.MainCoroutineRule
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import com.devmike.database.repository.CachedIssueRepo
import com.devmike.domain.models.IssueSearchModel
import com.devmike.network.networkSource.GitHubIssuesRepo
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PagedIssuesDataSourceTest {
    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    private lateinit var cachedIssueRepo: CachedIssueRepo
    private lateinit var gitHubIssuesRepo: GitHubIssuesRepo

    private lateinit var pagedDataSources: IssuePagedDataSources
    private lateinit var issueSearchModel: IssueSearchModel

    @Before
    fun setUp() {
        cachedIssueRepo = FakeCachedIssueRepo()
        gitHubIssuesRepo = FakeGithubRepo()
        issueSearchModel =
            IssueSearchModel(
                query = "test",
                repository = "flutter/flutter",
                issueState = "open",
                labels = emptyList(),
                assignees = emptyList(),
                cursor = null,
            )

        pagedDataSources =
            IssuePagedDataSources(
                FakeCachedIssueRepo(),
                FakeGithubRepo(),
                issueSearchModel,
            )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun refreshLoadSuccessAndEndOfPaginationWhenNoMoreData() =
        runTest {
            val remoteMediator =
                IssuePagedDataSources(cachedIssueRepo, gitHubIssuesRepo, issueSearchModel)
            val pagingState =
                PagingState<Int, IssueWithAssigneesAndLabels>(
                    emptyList(),
                    null,
                    PagingConfig(
                        pageSize = 3,
                        initialLoadSize = 3,
                        enablePlaceholders = true,
                    ),
                    0,
                )

            val result = remoteMediator.load(LoadType.REFRESH, pagingState)

            Assert.assertTrue(result is MediatorResult.Success)
            Assert.assertFalse((result as MediatorResult.Success).endOfPaginationReached)
        }
}
