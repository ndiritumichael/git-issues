package com.devmike.domain.models

data class IssueSearchModel(
    val query: String?,
    val repository: String,
    val issueState: String,
    val labels: List<String>?,
    val assignees: List<String>?,
    val sortBy: String? = null,
    val cursor: String? = null,
    val pageSize: Int = 10,
)
