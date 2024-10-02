package com.devmike.network.githubIssuesTest

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.devmike.network.githubIssuesTest.sampledata.repository.RepositoryPage1
import com.devmike.network.githubIssuesTest.sampledata.repository.RepositorySearchPage2
import com.devmike.network.githubIssuesTest.sampledata.repository.gqlerror
import com.devmike.network.networkSource.GitHubIssuesRepo
import com.devmike.network.networkSource.GitHubIssuesRepoImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.internal.closeQuietly
import org.junit.After
import org.junit.Before
import org.junit.Test

class RepositorySearchTest {
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
    fun `correct data returns success `() =
        runTest {
            // Enqueue a mock response on the MockWebServer

            val mockResponse =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .delayMillis(0)
                    .body(RepositorySearchPage2)
                    .build()

            val mockResponse2 =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .body(RepositoryPage1)
                    .build()
            mockWebServer.enqueue(mockResponse)
            mockWebServer.enqueue(mockResponse2)

            val data = gitHubIssuesRepo.searchRepositories("flutter")
            Truth.assertThat(data.isSuccess).isTrue()
            val result = data.getOrNull()
            Truth.assertThat(result).isNotNull()
            Truth.assertThat(result?.data?.size).isEqualTo(7)
            Truth.assertThat(result?.data?.get(0)?.name).isEqualTo("FlutterDouBan")

            val page2 = gitHubIssuesRepo.searchRepositories("flutter").getOrNull()
            Truth.assertThat(page2).isNotNull()
            Truth.assertThat(page2?.data?.size).isEqualTo(10)
            Truth.assertThat(page2?.data?.get(0)?.name).isEqualTo("flutter")
            Truth.assertThat(page2?.hasNextPage).isFalse()
        }

    @Test
    fun `searchRepositories returns failure on network error`() =
        runTest {
            mockWebServer.enqueue(
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .headers(mapOf("Content-Length" to "0"))
                    .body(emptyFlow())
                    .delayMillis(0)
                    .build(),
            )

            val result = gitHubIssuesRepo.searchRepositories("repo3", null, 10)

            Truth.assertThat(result.isFailure).isTrue()
        }

    @Test
    fun `searchRepositories returns failure on GraphQL error`() =
        runTest {
            val mockResponse =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .headers(mapOf("Content-Length" to "0"))
                    .delayMillis(0)
                    .body(
                        gqlerror,
                    ).build()
            mockWebServer.enqueue(mockResponse)

            val result = gitHubIssuesRepo.searchRepositories("repo4", null, 10)

            Truth.assertThat(result.isFailure).isTrue()
            Truth.assertThat(result.exceptionOrNull()?.message).isEqualTo("Some GraphQL error")
        }
}
