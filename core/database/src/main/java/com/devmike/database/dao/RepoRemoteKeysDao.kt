package com.devmike.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.devmike.database.entities.RemoteKeyEntity

@Dao
interface RepoRemoteKeysDao {
    @Query("SELECT * FROM remote_keys WHERE searchQuery = :query")
    suspend fun remoteKeyByQuery(query: String): RemoteKeyEntity?

    @Upsert
    suspend fun insertRemoteKey(remoteKey: RemoteKeyEntity)

    @Query("DELETE FROM remote_keys WHERE searchQuery = :query")
    suspend fun clearRemoteKeysForQuery(query: String)
}
