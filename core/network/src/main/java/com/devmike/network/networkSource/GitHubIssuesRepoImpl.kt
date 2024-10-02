package com.devmike.network.networkSource

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.devmike.domain.helper.IssueState
import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import com.devmike.network.GetRepositoryAssigneesQuery
import com.devmike.network.GetRepositoryLabelsQuery
import com.devmike.network.SearchIssuesQuery
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
                client.query(
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
            issueSearchModel: IssueSearchModel,
        ): Result<PagedDtoWrapper<List<IssueDTO>>> {
            val issueTest =
                safeApolloQuery {
                    client.query(
                        SearchIssuesQuery(
                            query = issueSearchModel.buildQuery(),
                            first = Optional.present(issueSearchModel.pageSize),
                            cursor = Optional.present(issueSearchModel.cursor),
                        ),
                    )
                }

            return issueTest.map { data ->

                PagedDtoWrapper(
                    nextCursor = data.search.pageInfo.endCursor,
                    hasNextPage = data.search.pageInfo.hasNextPage,
                    data = data.toIssues(),
                )
            }
        }

        override suspend fun getRepositoryLabels(
            name: String,
            owner: String,
            cursor: String?,
            pageSize: Int,
        ): Result<PagedDtoWrapper<List<LabelModel>>> =
            safeApolloQuery {
                client.query(
                    GetRepositoryLabelsQuery(
                        name = name,
                        owner = owner,
                        first = Optional.present(pageSize),
                        after = Optional.present(cursor),
                    ),
                )
            }.map { data ->
                PagedDtoWrapper(
                    nextCursor =
                        data.repository
                            ?.labels
                            ?.pageInfo
                            ?.endCursor,
                    hasNextPage =
                        data.repository
                            ?.labels
                            ?.pageInfo
                            ?.hasNextPage ?: false,
                    data =
                        data.repository?.labels?.edges?.mapNotNull { it?.node }?.map { node ->

                            LabelModel(
                                name = node.name,
                                color = node.color,
                            )
                        } ?: emptyList(),
                )
            }

        override suspend fun getRepositoryAssignees(
            name: String,
            owner: String,
            cursor: String?,
            pageSize: Int,
        ): Result<PagedDtoWrapper<List<AssigneeModel>>> =
            safeApolloQuery {
                client.query(
                    GetRepositoryAssigneesQuery(
                        name = name,
                        owner = owner,
                        first = Optional.present(pageSize),
                        after = Optional.present(cursor),
                    ),
                )
            }.map { data ->
                PagedDtoWrapper(
                    nextCursor =
                        data.repository
                            ?.assignableUsers
                            ?.pageInfo
                            ?.endCursor,
                    hasNextPage =
                        data.repository
                            ?.assignableUsers
                            ?.pageInfo
                            ?.hasNextPage ?: false,
                    data =
                        data.repository
                            ?.assignableUsers
                            ?.edges
                            ?.mapNotNull {
                                it?.node
                            }?.map { node ->

                                AssigneeModel(
                                    name = node.name,
                                    username = node.login,
                                    avatarUrl = node.avatarUrl.toString(),
                                )
                            } ?: emptyList(),
                )
            }

        // combine all filters for the search interface
        private fun IssueSearchModel.buildQuery(): String {
            val baseQuery = (this.query ?: "") + " is:issue"

            val repoFilter = "repo:$repository"

            val stateFilter = if (issueState == IssueState.ALL.state) "" else "state:$issueState"

            val labelsFilter = labels?.joinToString(" ") { "label:\"$it\"" } ?: ""

            val assigneesFilter = assignees?.joinToString(" ") { "assignee:$it" } ?: ""

            val sortFilter = sortBy?.let { "sort:$it" } ?: ""

            // Combine all filters
            return listOf(
                baseQuery,
                repoFilter,
                stateFilter,
                labelsFilter,
                assigneesFilter,
                sortFilter,
            ).filter { it.isNotEmpty() }.joinToString(" ")
        }
    }
