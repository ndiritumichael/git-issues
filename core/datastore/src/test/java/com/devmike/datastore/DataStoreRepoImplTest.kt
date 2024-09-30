package com.devmike.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.datastore.repo.DataStoreRepo
import com.devmike.datastore.repo.DataStoreRepoImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class DataStoreRepoImplTest {
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var dataStoreRepo: DataStoreRepo

    @Before
    fun setup() {
        dataStore =
            PreferenceDataStoreFactory.create {
                File.createTempFile("datastore", ".preferences_pb").apply { deleteOnExit() }
            }
        dataStoreRepo = DataStoreRepoImpl(dataStore)
    }

    @Test
    fun `Datastore should be initially null`() =
        runTest {
            val token = dataStoreRepo.getUserToken().first()

            Truth.assertThat(token).isNull()
        }

    @Test
    fun `insertToken should store token in DataStore`() =
        runTest {
            val testToken = "test_token"

            var token = dataStoreRepo.getUserToken().first()

            Truth.assertThat(token).isNull()
            dataStoreRepo.insertToken(testToken)

            token = dataStoreRepo.getUserToken().first()
            Truth.assertThat(token).isEqualTo(testToken)
        }

    @Test
    fun `insertToken should overwrite existing token`() =
        runTest {
            val initialToken = "initial_token"
            val newToken = "new_token"
            dataStoreRepo.insertToken(initialToken)
            dataStoreRepo.insertToken(newToken)

            val token = dataStoreRepo.getUserToken().first()
            Truth.assertThat(token).isEqualTo(newToken)
        }

    @Test
    fun `insertToken should handle empty token`() =
        runTest {
            val emptyToken = ""
            dataStoreRepo.insertToken(emptyToken)

            val token = dataStoreRepo.getUserToken().first()
            Truth.assertThat(token).isEqualTo(emptyToken)
        }

    @Test
    fun `clearUserToken should remove token from DataStore`() =
        runTest {
            val testToken = "test_token"
            dataStore.edit { it[stringPreferencesKey("token")] = testToken }
            val storedToken = dataStoreRepo.getUserToken().first()

            Truth.assertThat(storedToken).isEqualTo(testToken)

            dataStoreRepo.clearUserToken()

            val token = dataStoreRepo.getUserToken().first()
            Truth.assertThat(token).isNull()
        }
}
