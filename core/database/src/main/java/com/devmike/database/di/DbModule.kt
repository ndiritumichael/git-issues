package com.devmike.database.di

import android.content.Context
import androidx.room.Room
import com.devmike.database.GithubDatabase
import com.devmike.database.dao.RepositorySearchDAO
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
            ).build()

    @Singleton
    @Provides
    fun providesRepositoryDao(db: GithubDatabase): RepositorySearchDAO = db.getRepositoryDao()
}
