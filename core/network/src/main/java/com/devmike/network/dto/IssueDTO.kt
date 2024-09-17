package com.devmike.network.dto

import com.devmike.network.GetRepositoryDetailsQuery

data class IssueDTO(
    val state: String,
    val url: String,
    val title: String,
    val createdAt: String,
    val label: List<String>,
    val author: String,
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
            )
        } ?: emptyList()
