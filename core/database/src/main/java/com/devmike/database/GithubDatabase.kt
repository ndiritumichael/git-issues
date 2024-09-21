package com.devmike.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.devmike.database.dao.IssueKeyEntityDao
import com.devmike.database.dao.IssuesDao
import com.devmike.database.dao.RepoRemoteKeysDao
import com.devmike.database.dao.RepositorySearchDAO
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.CachedIssueKeyEntity
import com.devmike.database.entities.CachedRepository
import com.devmike.database.entities.RemoteKeyEntity

@Database(
    entities = [
        CachedRepository::class,
        RemoteKeyEntity::class,
        CachedIssueEntity::class,
        CachedIssueKeyEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun getRepositoryDao(): RepositorySearchDAO

    abstract fun remoteKeyDao(): RepoRemoteKeysDao

    abstract fun issueDao(): IssuesDao

    abstract fun issueKeyDao(): IssueKeyEntityDao
}
