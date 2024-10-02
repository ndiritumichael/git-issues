package com.devmike.domain.models

data class IssueModel(
    val id: String,
    val bodyText: String? = null,
    val state: String,
    val url: String,
    val title: String,
    val createdAt: String,
    val label: List<LabelModel>,
    val author: String,
    val repositoryName: String,
    val assignee: List<String>,
)
