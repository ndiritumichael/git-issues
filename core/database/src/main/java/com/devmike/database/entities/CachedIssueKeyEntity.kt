package com.devmike.database.entities

import androidx.room.Entity
import androidx.room.Index

/**
 * Create a composite primary key from the non-nullable fields.
 * Add add a unique index that includes all fields, including the nullable ones like @param[query]
 * This ensures uniqueness across all fields while allowing nulls.
*
 */

@Entity(
    primaryKeys = ["repoName", "assignee", "labels", "issueState"],
    indices = [
        Index(
            value = ["repoName", "assignee", "labels", "issueState", "query", "sortBy"],
            unique = true,
        ),
    ],
)
data class CachedIssueKeyEntity(
    val repoName: String,
    val nextCursor: String?,
    val assignee: List<String>,
    val labels: List<String>,
    val query: String?,
    val sortBy: String? = null,
    val issueState: String,
)
