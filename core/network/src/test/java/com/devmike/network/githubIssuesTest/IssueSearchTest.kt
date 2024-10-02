package com.devmike.network.githubIssuesTest

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.devmike.domain.models.IssueSearchModel
import com.devmike.network.githubIssuesTest.sampledata.repository.sampleIssueResultSearch
import com.devmike.network.networkSource.GitHubIssuesRepo
import com.devmike.network.networkSource.GitHubIssuesRepoImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.internal.closeQuietly
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class IssueSearchTest {
    private lateinit var mockWebServer: MockServer
    private lateinit var apolloClient: ApolloClient
    private lateinit var gitHubIssuesRepo: GitHubIssuesRepo

    @Before
    fun setup() =
        runTest {
            mockWebServer = MockServer()

            apolloClient =
                ApolloClient
                    .Builder()
                    .serverUrl(mockWebServer.url())
                    .okHttpClient(OkHttpClient.Builder().build())
                    .build()

            gitHubIssuesRepo = GitHubIssuesRepoImpl(apolloClient)
        }

    @After
    fun teardown() {
        mockWebServer.closeQuietly()
    }

    @Test
    fun`getRepositoryIssues returns success with multiple issues`() =
        runTest {
            // Arrange
            val mockResponse =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .body(
                        sampleIssueResultSearch,
                    ).build()
            mockWebServer.enqueue(mockResponse)
            val issueSearchModel =
                IssueSearchModel(
                    "impeller ios",
                    "flutter/flutter",
                    "cursor1",
                    listOf(),
                    listOf(),
                    null,
                    null,
                )

            val result = gitHubIssuesRepo.getRepositoryIssues(issueSearchModel)

            // Assert
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrNull()
            Truth.assertThat(data?.data?.size).isEqualTo(3)
            Truth.assertThat(data?.nextCursor).isEqualTo("Y3Vyc29yOjM=")
            Truth.assertThat(data?.hasNextPage).isTrue()
        }
}
