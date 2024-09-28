package com.devmike.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.devmike.database.convertors.Converters
import com.devmike.database.dao.IssueKeyEntityDao
import com.devmike.database.dao.IssuesDao
import com.devmike.database.dao.RepoRemoteKeysDao
import com.devmike.database.dao.RepositorySearchDAO
import com.devmike.database.entities.AssigneeEntity
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.CachedIssueKeyEntity
import com.devmike.database.entities.CachedRepository
import com.devmike.database.entities.LabelEntity
import com.devmike.database.entities.RemoteKeyEntity

@Database(
    entities = [
        CachedRepository::class,
        RemoteKeyEntity::class,
        CachedIssueEntity::class,
        CachedIssueKeyEntity::class,
        AssigneeEntity::class,
        LabelEntity::class,
    ],
    version = 2,
    exportSchema = true,
)
@TypeConverters(Converters::class)
abstract class GithubDatabase : RoomDatabase() {
    abstract fun getRepositoryDao(): RepositorySearchDAO

    abstract fun remoteKeyDao(): RepoRemoteKeysDao

    abstract fun issueDao(): IssuesDao

    abstract fun issueKeyDao(): IssueKeyEntityDao
}
