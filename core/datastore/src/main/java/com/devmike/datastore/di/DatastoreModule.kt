package com.devmike.datastore.di
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.devmike.datastore.repo.DataStoreRepo
import com.devmike.datastore.repo.DataStoreRepoImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Provides a singleton instance of [DataStore] for storing and retrieving application preferences.
 *
 * context to create the DataStore instance.
 *
 * @return A singleton instance of [DataStore] for storing and retrieving application preferences.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {
    @Provides
    @Singleton
    fun providesPreferences(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.dataStore
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreBindingModule {
    @Binds
    @Singleton
    abstract fun bindDataStoreRepo(dataStoreRepoImpl: DataStoreRepoImpl): DataStoreRepo
}

// At the top level of your kotlin file:
internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
