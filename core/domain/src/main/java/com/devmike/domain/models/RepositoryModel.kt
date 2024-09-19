package com.devmike.domain.models

data class RepositoryModel(
    val url: String,
    val name: String,
    val nameWithOwner: String,
    val owner: String,
    val description: String,
    val stargazers: Int,
    val forkCount: Int,
    val issueCount: Int,
)
