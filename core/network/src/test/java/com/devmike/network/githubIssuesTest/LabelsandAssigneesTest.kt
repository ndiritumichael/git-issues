package com.devmike.network.githubIssuesTest

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.network.okHttpClient
import com.apollographql.mockserver.MockResponse
import com.apollographql.mockserver.MockServer
import com.devmike.network.githubIssuesTest.sampledata.repository.FakeAssignees
import com.devmike.network.networkSource.GitHubIssuesRepo
import com.devmike.network.networkSource.GitHubIssuesRepoImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.internal.closeQuietly
import org.junit.After
import org.junit.Before
import org.junit.Test

class LabelsandAssigneesTest {
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
    fun `getAssignees returns success with multiple assignees`() =
        runTest {
            // Arrange
            val mockResponse =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .body(
                        FakeAssignees,
                    ).build()
            mockWebServer.enqueue(mockResponse)

            val result = gitHubIssuesRepo.getRepositoryAssignees("flutter", "flutter", null, 3)

            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrNull()
            Truth.assertThat(data?.data?.size).isEqualTo(3)
            Truth.assertThat(data?.nextCursor).isEqualTo("Y3Vyc29yOnYyOpKkYTE0bs4AEmlo")
            Truth.assertThat(data?.hasNextPage).isTrue()
        }

    @Test
    fun `getAssignees returns success with empty list`() =
        runTest {
            // Arrange
            val mockResponse =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .body(
                        """
                        {
                          "data": {
                            "repository": {
                              "__typename": "Repository",
                              "assignableUsers": {
                                "__typename": "UserConnection",
                                "pageInfo": {
                                  "__typename": "PageInfo",
                                  "endCursor": null,
                                  "hasNextPage": false
                                },
                                "edges": []
                              }
                            }
                          }
                        }
                        """.trimIndent(),
                    ).build()
            mockWebServer.enqueue(mockResponse)

            // Act (Replace with your actual function call)
            val result =
                gitHubIssuesRepo.getRepositoryAssignees(
                    "notflutter",
                    "notflutter",
                    null,
                    3,
                )

            // Assert
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrNull()
            Truth.assertThat(data?.data).isEmpty()
            Truth.assertThat(data?.nextCursor).isNull()
            Truth.assertThat(data?.hasNextPage).isFalse()
        }

    @Test
    fun `getLabels returns success with multiple labels`() =
        runTest {
            // Arrange
            val mockResponse =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .body(
                        """
                        {
                          "data": {
                            "repository": {
                              "__typename": "Repository",
                              "labels": {
                                "__typename": "LabelConnection",
                                "pageInfo":{
                                  "__typename": "PageInfo",
                                  "endCursor": "Mw",
                                  "hasNextPage": true
                                },
                                "edges": [
                                  {
                                    "__typename": "LabelEdge",
                                    "node": {
                                      "__typename": "Label",
                                      "name": "a: tests",
                                      "color": "55ddbb"
                                    }
                                  },
                                  {
                                    "__typename": "LabelEdge",
                                    "node": {
                                      "__typename": "Label",
                                      "name": "a: text input",
                                      "color": "55ddbb"
                                    }
                                  },
                                  {
                                    "__typename": "LabelEdge",
                                    "node": {
                                      "__typename": "Label",
                                      "name": "c: new feature",
                                      "color": "e02000"
                                    }
                                  }
                                ]
                              }
                            }
                          }
                        }
                        """.trimIndent(),
                    ).build()
            mockWebServer.enqueue(mockResponse)

            // Act (Replace with your actual function call)
            val result =
                gitHubIssuesRepo.getRepositoryLabels(
                    "flutter",
                    "flutter",
                    null,
                    3,
                )

            // Assert
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrNull()
            Truth.assertThat(data?.data?.size).isEqualTo(3)
            Truth.assertThat(data?.nextCursor).isEqualTo("Mw")
            Truth.assertThat(data?.hasNextPage).isTrue()
            // You can add more assertions for individual label properties if needed
        }

    @Test
    fun `getLabels returns success with empty list`() =
        runTest {
            val mockResponse =
                MockResponse
                    .Builder()
                    .statusCode(200)
                    .body(
                        """
                        {
                          "data": {
                            "repository": {
                              "__typename": "Repository",
                              "labels": {
                                "__typename": "LabelConnection",
                                "pageInfo": {
                                  "__typename": "PageInfo",
                                  "endCursor": null,
                                  "hasNextPage": false
                                },
                                "edges": []
                              }
                            }
                          }
                        }
                        """.trimIndent(),
                    ).build()
            mockWebServer.enqueue(mockResponse)

            // Act (Replace with your actual function call)
            val result =
                gitHubIssuesRepo.getRepositoryLabels(
                    "notflutter",
                    "notflutter",
                    null,
                    3,
                )

            // Assert
            Truth.assertThat(result.isSuccess).isTrue()
            val data = result.getOrNull()
            Truth.assertThat(data?.data).isEmpty()
            Truth.assertThat(data?.nextCursor).isNull()
            Truth.assertThat(data?.hasNextPage).isFalse()
        }
}
