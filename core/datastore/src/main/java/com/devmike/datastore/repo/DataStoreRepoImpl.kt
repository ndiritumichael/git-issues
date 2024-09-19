package com.devmike.datastore.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepoImpl
    @Inject
    constructor(
        private val preferences: DataStore<Preferences>,
    ) : DataStoreRepo {
        private val tokenPreference = stringPreferencesKey("token")

        override suspend fun insertToken(token: String) {
            preferences.edit {
                it[tokenPreference] = token
            }
        }

        override fun getUserToken(): Flow<String?> =
            preferences.data.map {
                it[tokenPreference]
            }

        override suspend fun clearUserToken() {
            preferences.edit {
                it.remove(tokenPreference)
            }
        }
    }
