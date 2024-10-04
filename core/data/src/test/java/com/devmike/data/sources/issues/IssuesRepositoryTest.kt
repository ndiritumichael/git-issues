package com.devmike.data.sources.issues

import androidx.paging.testing.asSnapshot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.data.testdoubles.FakeCachedIssueRepo
import com.devmike.data.testdoubles.FakeGithubRepo
import com.devmike.data.util.MainCoroutineRule
import com.devmike.domain.models.IssueSearchModel
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IssuesRepositoryTest {
    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getPagedIssues_returnsFlowOfPagingDataWithIssueModels() =
        runTest {
            val repository =
                IssuesRepositoryImpl(
                    cachedIssueRepo = FakeCachedIssueRepo(),
                    gitHubIssuesRepo = FakeGithubRepo(),
                )

            val issueSearchModel =
                IssueSearchModel(
                    query = "test",
                    repository = "flutter/flutter",
                    issueState = "open",
                    labels = emptyList(),
                    assignees = emptyList(),
                )

            val flow = repository.getPagedIssues(issueSearchModel)

            // Collect the flow and assert on the PagingData
            val pagingData = flow.asSnapshot()

// first emission will be empty
            Truth.assertThat(pagingData).isEmpty()

            val pagingData2 = flow.asSnapshot()

            Truth.assertThat(pagingData2).isNotEmpty()
            Truth.assertThat(pagingData2.size).isEqualTo(3)
        }
}
