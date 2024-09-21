package com.devmike.database.entities

import androidx.room.Entity

@Entity(tableName = "repositories", primaryKeys = ["name", "owner"])
data class CachedRepository(
    val url: String,
    val name: String,
    val nameWithOwner: String,
    val owner: String,
    val description: String,
    val stargazers: Int,
    val forkCount: Int,
    val issueCount: Int,
    val searchQuery: String,
    val avatarUrl: String,
)
