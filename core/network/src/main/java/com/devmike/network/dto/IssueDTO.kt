package com.devmike.network.dto

import com.devmike.network.GetRepositoryDetailsQuery
import com.devmike.network.SearchIssuesQuery

data class IssueDTO(
    val id: String,
    val bodyText: String? = null,
    val state: String,
    val url: String,
    val title: String,
    val createdAt: String,
    val labels: List<IssueLabel>,
    val author: String,
    val issueCommentsCount: Int,
    val repositoryName: String,
    val assignees: List<String> = emptyList(),
)

data class IssueLabel(
    val name: String,
    val color: String,
)

fun GetRepositoryDetailsQuery.Data.toIssues(): List<IssueDTO> =
    this.repository
        ?.issues
        ?.edges
        ?.filterNotNull()
        ?.mapNotNull {
            it.node
        }?.map { node ->
            IssueDTO(
                state = node.state.rawValue,
                url = node.url.toString(),
                title = node.title,
                createdAt = node.createdAt.toString(),
                author = node.author?.login ?: "No Author",
                labels =
                    node.labels
                        ?.edges
                        ?.filterNotNull()
                        ?.mapNotNull { label ->

                            label.node?.let {
                                IssueLabel(
                                    name = it.name,
                                    color = it.color,
                                )
                            }
                        } ?: listOf(),
                id = node.id,
                bodyText = node.bodyText,
                issueCommentsCount = node.comments.totalCount,
                repositoryName = this.repository.nameWithOwner,
                assignees = node.assignees.nodes?.mapNotNull { it?.name } ?: emptyList(),
            )
        } ?: emptyList()

fun SearchIssuesQuery.Data.toIssues(): List<IssueDTO> =
    this.search.nodes?.filterNotNull()?.mapNotNull { node -> node.onIssue }?.map { node ->

        IssueDTO(
            state = node.state.rawValue,
            url = node.url.toString(),
            title = node.title,
            createdAt = node.createdAt.toString(),
            author = node.author?.login ?: "No Author",
            labels =
                node.labels
                    ?.edges
                    ?.filterNotNull()
                    ?.mapNotNull { label ->
                        label.node?.let {
                            IssueLabel(
                                name = it.name,
                                color = it.color,
                            )
                        }
                    } ?: listOf(),
            id = node.id,
            bodyText = null,
            issueCommentsCount = node.comments.totalCount,
            repositoryName = node.repository.nameWithOwner,
            assignees = node.assignees.nodes?.mapNotNull { it?.login } ?: emptyList(),
        )
    } ?: emptyList()
