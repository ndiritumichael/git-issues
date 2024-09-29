package com.devmike.database.daotest

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.database.dao.IssueKeyEntityDao
import com.devmike.database.entities.CachedIssueKeyEntity
import com.devmike.database.helpers.BaseDbTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class IssueKeyEntityDaoTest : BaseDbTest() {
    private lateinit var issueKeyEntityDao: IssueKeyEntityDao

    @Before
    fun setUp() {
        issueKeyEntityDao = db.issueKeyDao()
    }

    @Test
    fun getIssuesKey_returnsCorrectEntity() =
        runTest {
            val key1 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-1234567890",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug,network,priority-high"),
                    issueState = "open",
                    query = null,
                )
            issueKeyEntityDao.insertRemoteKey(key1)

            val retrievedKey =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug,network,priority-high"),
                    issueState = "open",
                    query = null,
                )

            Truth.assertThat(retrievedKey).isEqualTo(key1)
        }

    @Test
    fun getIssuesKey_returnsNull_whenKeyDoesNotExist() =
        runTest {
            val retrievedKey =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "nonexistent-organization/nonexistent-repo",
                    assignee = listOf("john.doe@company.com"),
                    labels = listOf("bug,feature"),
                    issueState = "open",
                    query = "jacoco",
                )

            Truth.assertThat(retrievedKey).isNull()
        }

    @Test
    fun getIssuesKey_ignoresAssigneeAndLabels_whenNull() =
        runTest {
            val key1 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-1234567890",
                    assignee = emptyList(),
                    labels = emptyList(),
                    issueState = "open",
                    query = null,
                )
            issueKeyEntityDao.insertRemoteKey(key1)

            val retrievedKey =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf(),
                    labels = listOf(),
                    issueState = "open",
                    query = null,
                )

            Truth.assertThat(retrievedKey).isEqualTo(key1)
        }

    @Test
    fun getIssuesKey_returnsCorrectEntity_whenAssigneeAndLabelsAreNull() =
        runTest {
            val key1 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-1234567890",
                    assignee = listOf(),
                    labels = listOf(),
                    issueState = "open",
                    query = "okhttp",
                )
            issueKeyEntityDao.insertRemoteKey(key1)

            val retrievedKey =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf(),
                    labels = listOf(),
                    issueState = "open",
                    query = "okhttp",
                )

            Truth.assertThat(retrievedKey).isEqualTo(key1)
        }

    @Test
    fun insertRemoteKey_updatesExistingKey() =
        runTest {
            val key1 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-1234567890",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug,network,priority-high"),
                    issueState = "open",
                    query = null,
                )
            issueKeyEntityDao.insertRemoteKey(key1)

            val updatedKey =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-abcdefghij",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug,network,priority-high"),
                    issueState = "open",
                    query = null,
                )
            issueKeyEntityDao.insertRemoteKey(updatedKey)

            val retrievedKey =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )

            Truth.assertThat(retrievedKey).isEqualTo(updatedKey)
        }

    @Test
    fun clearRemoteKeysForQuery_clearsCorrectKeys() =
        runTest {
            val key1 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-1234567890",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            val key2 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-abcdefghij",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            val key3 =
                CachedIssueKeyEntity(
                    repoName = "another-organization/another-repo",
                    nextCursor = "cursor-0123456789",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            issueKeyEntityDao.insertRemoteKey(key1)
            issueKeyEntityDao.insertRemoteKey(key2)
            issueKeyEntityDao.insertRemoteKey(key3)

            issueKeyEntityDao.clearRemoteKeysForQuery(
                repoName = "square/okhttp",
                assignee = listOf("square/okhttp"),
                labels = listOf("bug", "network", "priority-high"),
                query = null,
                issueState = "open",
            )

            val retrievedKey1 =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            val retrievedKey2 =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            val retrievedKey3 =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "another-organization/another-repo",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )

            Truth.assertThat(retrievedKey1).isNull()
            Truth.assertThat(retrievedKey2).isNotNull()
            Truth.assertThat(retrievedKey3).isNotNull()
        }

    @Test
    fun clearRemoteKeysForQuery_ignoresAssigneeAndLabels_whenNull() =
        runTest {
            val key1 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-1234567890",
                    assignee = listOf(),
                    labels = listOf(),
                    issueState = "open",
                    query = null,
                )
            val key2 =
                CachedIssueKeyEntity(
                    repoName = "square/okhttp",
                    nextCursor = "cursor-abcdefghij",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            val key3 =
                CachedIssueKeyEntity(
                    repoName = "another-organization/another-repo",
                    nextCursor = "cursor-0123456789",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            issueKeyEntityDao.insertRemoteKey(key1)
            issueKeyEntityDao.insertRemoteKey(key2)
            issueKeyEntityDao.insertRemoteKey(key3)

            issueKeyEntityDao.clearRemoteKeysForQuery(
                repoName = "square/okhttp",
                assignee = listOf(),
                labels = listOf(),
                query = null,
                issueState = "open",
            )

            val retrievedKey1 =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf(),
                    labels = listOf(),
                    issueState = "open",
                    query = null,
                )
            val retrievedKey2 =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "square/okhttp",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )
            val retrievedKey3 =
                issueKeyEntityDao.getIssuesKey(
                    repoName = "another-organization/another-repo",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network", "priority-high"),
                    issueState = "open",
                    query = null,
                )

            Truth.assertThat(retrievedKey1).isNull()
            Truth.assertThat(retrievedKey2).isNotNull()
            Truth.assertThat(retrievedKey3).isNotNull()
        }
}
