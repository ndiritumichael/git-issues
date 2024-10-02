package com.devmike.database.daotest

import androidx.paging.PagingSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.database.dao.IssuesDao
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.helpers.BaseDbTest
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class IssuesDaoTest : BaseDbTest() {
    private lateinit var issuesDao: IssuesDao

    @Before
    fun setUp() {
        issuesDao = db.issueDao()
    }

    @Test
    fun insertAndGetIssues_success() =
        runTest {
            val issue1 =
                CachedIssueEntity(
                    issueId = "12345",
                    bodyText = "The app crashes when clicking the save button.",
                    state = "open",
                    url = "https://github.com/real-repo/issues/12345",
                    title = "App crash on save",
                    createdAt = "2024-09-21T10:30:00Z",
                    author = "johndoe",
                    repositoryName = "real-repo",
                )

            val issue2 =
                CachedIssueEntity(
                    issueId = "12346",
                    bodyText = "Feature request to add dark mode.",
                    state = "closed",
                    url = "https://github.com/real-repo/issues/12346",
                    title = "Dark mode support",
                    createdAt = "2024-09-22T12:45:00Z",
                    author = "janedoe",
                    repositoryName = "real-repo",
                )

            issuesDao.insertIssues(listOf(issue1, issue2))

            val pagingSource =
                issuesDao.getRepositoryIssues("real-repo").load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            Truth.assertThat(pagingSource.data).containsExactly(issue1, issue2)
        }

    @Test
    fun insertIssues_replaceOnConflict() =
        runTest {
            val issue1 =
                CachedIssueEntity(
                    issueId = "12345",
                    bodyText = "The app crashes when clicking the save button.",
                    state = "open",
                    url = "https://github.com/real-repo/issues/12345",
                    title = "App crash on save",
                    createdAt = "2024-09-21T10:30:00Z",
                    author = "johndoe",
                    repositoryName = "real-repo",
                )

            val issueUpdated =
                CachedIssueEntity(
                    issueId = "12345",
                    bodyText = "Crash fixed in the latest release.",
                    state = "closed",
                    url = "https://github.com/real-repo/issues/12345",
                    title = "App crash on save (fixed)",
                    createdAt = "2024-09-21T10:30:00Z",
                    author = "johndoe",
                    repositoryName = "real-repo",
                )

            // Insert original issue
            issuesDao.insertIssues(listOf(issue1))

            // Insert updated issue (same ID, should replace)
            issuesDao.insertIssues(listOf(issueUpdated))

            val pagingSource =
                issuesDao.getRepositoryIssues("real-repo").load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            Truth.assertThat(pagingSource.data).containsExactly(issueUpdated)
            Truth.assertThat(pagingSource.data).doesNotContain(issue1)
        }

    @Test
    fun deleteRepositoryIssues_success() =
        runTest {
            val issue1 =
                CachedIssueEntity(
                    issueId = "12345",
                    bodyText = "The app crashes when clicking the save button.",
                    state = "open",
                    url = "https://github.com/real-repo/issues/12345",
                    title = "App crash on save",
                    createdAt = "2024-09-21T10:30:00Z",
                    author = "johndoe",
                    repositoryName = "real-repo",
                )

            val issue2 =
                CachedIssueEntity(
                    issueId = "54321",
                    bodyText = "Unable to log in with Google account.",
                    state = "closed",
                    url = "https://github.com/another-repo/issues/54321",
                    title = "Google login issue",
                    createdAt = "2024-08-10T14:15:00Z",
                    author = "alice",
                    repositoryName = "another-repo",
                )

            issuesDao.insertIssues(listOf(issue1, issue2))

            // Delete issues from real-repo
            issuesDao.deleteRepositoryIssues("real-repo")

            val pagingSource =
                issuesDao.getRepositoryIssues("real-repo").load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            Truth.assertThat(pagingSource.data).isEmpty()

            val anotherRepoPagingSource =
                issuesDao.getRepositoryIssues("another-repo").load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            Truth.assertThat(anotherRepoPagingSource.data).containsExactly(issue2)
        }

    @Test
    fun getRepositoryIssues_returnsEmpty_whenNoIssuesExist() =
        runTest {
            val pagingSource =
                issuesDao.getRepositoryIssues("nonexistent-repo").load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            Truth.assertThat(pagingSource.data).isEmpty()
        }

    @Test
    fun insertIssue_withNullFields() =
        runTest {
            val issue =
                CachedIssueEntity(
                    issueId = "67890",
                    bodyText = null,
                    state = "open",
                    url = "https://github.com/real-repo/issues/67890",
                    title = "Issue with null body",
                    createdAt = "2024-09-23T08:00:00Z",
                    author = "user3",
                    repositoryName = "real-repo",
                )

            issuesDao.insertIssues(listOf(issue))

            val pagingSource =
                issuesDao.getRepositoryIssues("real-repo").load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            Truth.assertThat(pagingSource.data).containsExactly(issue)
            Truth.assertThat(pagingSource.data.first().bodyText).isNull()
        }
}
