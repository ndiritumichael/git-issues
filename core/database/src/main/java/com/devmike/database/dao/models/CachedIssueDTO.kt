package com.devmike.database.dao.models

data class CachedIssueDTO(
    val assignee: String? = null,
    val labels: List<String> = emptyList(),
    val repository: String,
)
