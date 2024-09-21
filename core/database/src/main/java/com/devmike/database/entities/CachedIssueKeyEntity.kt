package com.devmike.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["repoName"])
data class CachedIssueKeyEntity(
    val repoName: String,
    val nextCursor: String?,
    val assignee: String? = null,
    val labels: String = "",
)
