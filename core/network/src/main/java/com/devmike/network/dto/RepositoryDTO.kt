package com.devmike.network.dto

import com.devmike.network.SearchRepositoriesQuery

data class RepositoryDTO(
    val url: String,
    val name: String,
    val nameWithOwner: String,
    val owner: String,
    val description: String,
    val stargazers: Int,
    val forkCount: Int,
    val issueCount: Int,
)

fun SearchRepositoriesQuery.Data.toRepositoryDTOs(): List<RepositoryDTO> =
    this.search.nodes
        ?.filterNotNull()
        ?.mapNotNull {
            it.onRepository
        }?.map {
            RepositoryDTO(
                url = it.url.toString(),
                name = it.name,
                nameWithOwner = it.nameWithOwner,
                owner = it.nameWithOwner,
                description = it.description ?: "No Description Available",
                stargazers = it.stargazers.totalCount,
                forkCount = it.forkCount,
                issueCount = it.issues.totalCount,
            )
        } ?: emptyList()
