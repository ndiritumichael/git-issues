package com.devmike.domain.models

data class IssueModel(
    val state: String,
    val url: String,
    val title: String,
    val createdAt: String,
    val label: List<String>,
    val author: String,
)
