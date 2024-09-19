package com.devmike.database.dao

import androidx.room.Query
import androidx.room.Upsert
import com.devmike.database.entities.RemoteKeyEntity

interface RemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE searchQuery = :query AND type = :type")
    suspend fun remoteKeyByQuery(
        query: String,
        type: String,
    ): RemoteKeyEntity?

    @Upsert
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("DELETE FROM remote_keys WHERE searchQuery = :query AND type = :type")
    suspend fun clearRemoteKeysForQuery(
        query: String,
        type: String,
    )
}
