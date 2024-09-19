package com.devmike.network.networkSource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.devmike.network.GetRepositoryDetailsQuery
import com.devmike.network.SearchRepositoriesQuery
import com.devmike.network.dto.IssueDTO
import com.devmike.network.dto.PagedDtoWrapper
import com.devmike.network.dto.RepositoryDTO
import com.devmike.network.dto.toIssues
import com.devmike.network.dto.toRepositoryDTOs
import com.devmike.network.utils.safeApolloQuery
import javax.inject.Inject

class GitHubIssuesRepoImpl
    @Inject
    constructor(
        private val client: ApolloClient,
    ) : GitHubIssuesRepo {
        override suspend fun searchRepositories(
            name: String,
            cursor: String?,
            pageSize: Int,
        ): Result<PagedDtoWrapper<List<RepositoryDTO>>> =
            safeApolloQuery {
                client
                    .query(
                        SearchRepositoriesQuery(
                            query = name,
                            first = Optional.present(pageSize),
                            after = Optional.present(cursor),
                        ),
                    )
            }.map { data ->
                PagedDtoWrapper(
                    nextCursor = data.search.pageInfo.endCursor,
                    hasNextPage = data.search.pageInfo.hasNextPage,
                    data = data.toRepositoryDTOs(),
                )
            }

        override suspend fun getRepositoryIssues(
            name: String,
            owner: String,
            cursor: String?,
            pageSize: Int,
        ): Result<PagedDtoWrapper<List<IssueDTO>>> {
            val result =
                safeApolloQuery {
                    client.query(
                        GetRepositoryDetailsQuery(
                            name = name,
                            owner = owner,
                            first = Optional.present(pageSize),
                            after = Optional.present(cursor),
                        ),
                    )
                }

            return result.map { data ->
                PagedDtoWrapper(
                    nextCursor =
                        data
                            .repository
                            ?.issues
                            ?.pageInfo
                            ?.endCursor,
                    hasNextPage =
                        data
                            .repository
                            ?.issues
                            ?.pageInfo
                            ?.hasNextPage ?: false,
                    data = data.toIssues(),
                )
            }
        }
    }
