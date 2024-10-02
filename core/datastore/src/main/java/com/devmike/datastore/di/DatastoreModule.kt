package com.devmike.datastore.di
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.devmike.datastore.repo.DataStoreRepo
import com.devmike.datastore.repo.DataStoreRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {
    @Provides
    @Singleton
    fun provideDataStoreRepository(preferences: DataStore<Preferences>): DataStoreRepo =
        DataStoreRepoImpl(preferences)

    @Provides
    @Singleton
    fun providesPreferences(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.dataStore
}

// At the top level of your kotlin file:
internal val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
