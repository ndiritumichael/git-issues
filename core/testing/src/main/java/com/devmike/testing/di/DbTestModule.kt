package com.devmike.testing.di

import android.content.Context
import androidx.room.Room
import com.devmike.database.GithubDatabase
import com.devmike.database.di.DbModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DbModule::class],
)
object DbTestModule {
    @Provides
    @Singleton
    fun providesGithubDatabase(
        @ApplicationContext context: Context,
    ): GithubDatabase =
        Room
            .inMemoryDatabaseBuilder(
                context,
                GithubDatabase::class.java,
            ).allowMainThreadQueries()
            .build()
}
