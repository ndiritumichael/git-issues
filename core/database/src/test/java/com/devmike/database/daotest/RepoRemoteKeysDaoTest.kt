package com.devmike.database.daotest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.database.dao.RepoRemoteKeysDao
import com.devmike.database.entities.RemoteKeyEntity
import com.devmike.database.helpers.BaseDbTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class RepoRemoteKeysDaoTest : BaseDbTest() {
    private lateinit var keyDao: RepoRemoteKeysDao

    @Before
    fun setUp() {
        keyDao = db.remoteKeyDao()
    }

    @Test
    fun insertAndGetRemoteKey_success() =
        runTest {
            val remoteKey =
                RemoteKeyEntity(
                    searchQuery = "android_development",
                    nextCursor = "cursor_1234",
                )

            keyDao.insertRemoteKey(remoteKey)

            val result = keyDao.remoteKeyByQuery("android_development")
            Truth.assertThat(result).isEqualTo(remoteKey)
        }

    @Test
    fun insertMultipleRemoteKeys_success() =
        runTest {
            val remoteKey1 =
                RemoteKeyEntity(searchQuery = "kotlin_tutorials", nextCursor = "cursor_5678")
            val remoteKey2 =
                RemoteKeyEntity(searchQuery = "jetpack_compose_guides", nextCursor = "cursor_91011")

            keyDao.insertRemoteKey(remoteKey1)
            keyDao.insertRemoteKey(remoteKey2)

            val result1 = keyDao.remoteKeyByQuery("kotlin_tutorials")
            val result2 = keyDao.remoteKeyByQuery("jetpack_compose_guides")

            Truth.assertThat(remoteKey1).isEqualTo(result1)
            Truth.assertThat(remoteKey2).isEqualTo(result2)
        }

    @Test
    fun remoteKeyByQuery_noSuchQuery_returnsNull() =
        runTest {
            val result = keyDao.remoteKeyByQuery("non_existent_topic")
            Truth.assertThat(result).isNull()
        }

    @Test
    fun clearRemoteKeysForQuery_success() =
        runTest {
            val remoteKey =
                RemoteKeyEntity(
                    searchQuery = "flutter_beginners",
                    nextCursor = "cursor_31415",
                )

            keyDao.insertRemoteKey(remoteKey)
            var result = keyDao.remoteKeyByQuery("flutter_beginners")
            Truth.assertThat(result).isEqualTo(remoteKey)

            keyDao.clearRemoteKeysForQuery("flutter_beginners")
            result = keyDao.remoteKeyByQuery("flutter_beginners")
            Truth.assertThat(result).isNull()
        }

    @Test
    fun insertRemoteKey_withNullNextCursor_success() =
        runTest {
            val remoteKey =
                RemoteKeyEntity(searchQuery = "ios_swift_advanced", nextCursor = null)

            keyDao.insertRemoteKey(remoteKey)

            val result = keyDao.remoteKeyByQuery("ios_swift_advanced")
            Truth.assertThat(remoteKey).isEqualTo(result)
            Truth.assertThat(result?.nextCursor).isNull()
        }

    @Test
    fun upsertRemoteKey_updatesExistingKey_success() =
        runTest {
            val remoteKey =
                RemoteKeyEntity(
                    searchQuery = "kotlin_coroutines",
                    nextCursor = "cursor_1112",
                )
            keyDao.insertRemoteKey(remoteKey)

            val updatedRemoteKey =
                RemoteKeyEntity(
                    searchQuery = "kotlin_coroutines",
                    nextCursor = "cursor_1314",
                )
            keyDao.insertRemoteKey(updatedRemoteKey)

            val result = keyDao.remoteKeyByQuery("kotlin_coroutines")
            Truth.assertThat(updatedRemoteKey).isEqualTo(result)
        }

    @Test
    fun insertRemoteKey_lastUpdated_isSetCorrectly() =
        runTest {
            val currentTime = System.currentTimeMillis()
            val remoteKey =
                RemoteKeyEntity(
                    searchQuery = "android_architecture_components",
                    nextCursor = "cursor_1415",
                    lastUpdated = currentTime,
                )

            keyDao.insertRemoteKey(remoteKey)

            val result = keyDao.remoteKeyByQuery("android_architecture_components")
            Truth.assertThat(currentTime).isEqualTo(result?.lastUpdated)
        }

    @Test
    fun upsertRemoteKey_doesNotDuplicate_success() =
        runTest {
            val remoteKey =
                RemoteKeyEntity(
                    searchQuery = "multiplatform_kotlin",
                    nextCursor = "cursor_314159",
                )

            keyDao.insertRemoteKey(remoteKey)
            keyDao.insertRemoteKey(remoteKey) // Insert same key again

            val result = keyDao.remoteKeyByQuery("multiplatform_kotlin")
            Truth.assertThat(remoteKey).isEqualTo(result)
        }
}
