package com.devmike.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeyEntity(
    @PrimaryKey val searchQuery: String,
    val nextCursor: String?,
    val lastUpdated: Long = System.currentTimeMillis(),
)
