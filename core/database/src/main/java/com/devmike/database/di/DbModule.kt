package com.devmike.database.di

import android.content.Context
import androidx.room.Room
import com.devmike.database.GithubDatabase
import com.devmike.database.dao.IssueKeyEntityDao
import com.devmike.database.dao.IssuesDao
import com.devmike.database.dao.RepoRemoteKeysDao
import com.devmike.database.dao.RepositorySearchDAO
import com.devmike.database.repository.CachedIssueRepo
import com.devmike.database.repository.CachedIssueRepoImpl
import com.devmike.database.repository.CachedRepoSearch
import com.devmike.database.repository.CachedRepoSearchImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @Provides
    @Singleton
    fun providesGithubDatabase(
        @ApplicationContext context: Context,
    ): GithubDatabase =
        Room
            .databaseBuilder(
                context,
                GithubDatabase::class.java,
                "githubdb",
            ).fallbackToDestructiveMigration()
            .build()
}

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Singleton
    @Provides
    fun providesRepositoryDao(db: GithubDatabase): RepositorySearchDAO = db.getRepositoryDao()

    @Singleton
    @Provides
    fun providesRemoteKeyDao(db: GithubDatabase): RepoRemoteKeysDao = db.remoteKeyDao()

    @Singleton
    @Provides
    fun providesIssueDao(db: GithubDatabase): IssuesDao = db.issueDao()

    @Singleton
    @Provides
    fun providesCachedRepo(db: GithubDatabase): CachedRepoSearch = CachedRepoSearchImpl(db)

    @Singleton
    @Provides
    fun providesIssueKeysDao(db: GithubDatabase): IssueKeyEntityDao = db.issueKeyDao()

    @Singleton
    @Provides
    fun providescachedIssueRepo(db: GithubDatabase): CachedIssueRepo = CachedIssueRepoImpl(db)
}
