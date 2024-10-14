package com.devmike.testing.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import com.devmike.datastore.di.DatastoreModule
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import java.io.File
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatastoreModule::class],
)
object DatastoreTestModule {
    @Provides
    @Singleton
    fun providesPreferences(): DataStore<Preferences> =
        PreferenceDataStoreFactory.create {
            File.createTempFile("datastore", ".preferences_pb").apply { deleteOnExit() }
        }
}
