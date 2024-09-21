package com.devmike.network.dto

import com.devmike.network.GetRepositoryDetailsQuery

data class IssueDTO(
    val id: String,
    val bodyText: String? = null,
    val state: String,
    val url: String,
    val title: String,
    val createdAt: String,
    val label: List<String>,
    val author: String,
    val issueCommentsCount: Int,
    val repositoryName: String,
    val assignees: List<String> = emptyList(),
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
                label =
                    node.labels
                        ?.edges
                        ?.filterNotNull()
                        ?.mapNotNull { it.node?.name } ?: listOf(),
                id = node.id,
                bodyText = node.bodyText,
                issueCommentsCount = node.comments.totalCount,
                repositoryName = this.repository.nameWithOwner,
                assignees = node.assignees.nodes?.mapNotNull { it?.name } ?: emptyList(),
            )
        } ?: emptyList()
