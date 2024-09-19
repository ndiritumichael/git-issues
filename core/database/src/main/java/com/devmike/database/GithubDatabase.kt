package com.devmike.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devmike.database.dao.RepositorySearchDAO
import com.devmike.database.entities.CachedRepository
import com.devmike.database.entities.RemoteKeyEntity

@Database(
    entities = [
        CachedRepository::class,
        RemoteKeyEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun getRepositoryDao(): RepositorySearchDAO
}
