package com.devmike.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "issues")
data class CachedIssueEntity(
    @PrimaryKey
    val id: String,
    val bodyText: String? = null,
    val state: String,
    val url: String,
    val title: String,
    val createdAt: String,
    val label: List<String>,
    val author: String,
    val repositoryName: String,
)
