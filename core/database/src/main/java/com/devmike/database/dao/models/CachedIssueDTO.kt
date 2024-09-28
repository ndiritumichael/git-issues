package com.devmike.database.dao.models

data class CachedIssueDTO(
    val assignee: List<String> = emptyList(),
    val labels: List<String> = emptyList(),
    val repository: String,
    val sortBy: String? = null,
    val query: String?,
    val issueStatus: String,
)
