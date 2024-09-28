package com.devmike.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(
            value = [
                "repoName",
                "query", "assignee",
                "labels", "nextCursor",
                "sortBy",
                "issueState",
            ],
            unique = true,
        ),
    ],
)
data class CachedIssueKeyEntity(
    @PrimaryKey val repoName: String,
    val nextCursor: String?,
    val assignee: List<String>,
    val labels: List<String>,
    val query: String?,
    val sortBy: String? = null,
    val issueState: String,
)
