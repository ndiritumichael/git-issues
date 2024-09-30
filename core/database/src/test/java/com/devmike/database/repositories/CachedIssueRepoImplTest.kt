package com.devmike.database.repositories

import androidx.paging.PagingSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.AssigneeEntity
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import com.devmike.database.entities.LabelEntity
import com.devmike.database.helpers.BaseDbTest
import com.devmike.database.repository.CachedIssueRepoImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CachedIssueRepoImplTest : BaseDbTest() {
    private lateinit var cachedIssueRepoImpl: CachedIssueRepoImpl

    @Before
    fun setUp() {
        cachedIssueRepoImpl = CachedIssueRepoImpl(db)
    }

    @Test
    fun insertIssues_withRefresh_shouldClearAndInsertNewData() =
        runTest {
            // Prepare test data
            val issueDTO =
                CachedIssueDTO(
                    repository = "square/okhttp",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network"),
                    query = "okhttp",
                    issueStatus = "open",
                )
            val initialIssues =
                listOf(
                    IssueWithAssigneesAndLabels(
                        CachedIssueEntity(
                            issueId = "issue_id_1",
                            bodyText = "Initial body 1",
                            state = "open",
                            url = "https://github.com/square/okhttp/issues/1",
                            title = "Initial title 1",
                            createdAt = "2023-09-20T00:00:00Z",
                            author = "octocat",
                            repositoryName = "square/okhttp",
                        ),
                        labels = listOf(),
                        assignees = listOf(),
                    ),
                )

            // Insert initial data
            cachedIssueRepoImpl.insertIssues(false, "cursor1", issueDTO, initialIssues)

            // Insert new data with refresh
            val newIssues =
                listOf(
                    IssueWithAssigneesAndLabels(
                        assignees = listOf(),
                        labels = listOf(),
                        issue =

                            CachedIssueEntity(
                                issueId = "issue_id_2",
                                bodyText = "New body 2",
                                state = "open",
                                url = "https://github.com/square/okhttp/issues/2",
                                title = "New title 2",
                                createdAt = "2023-09-21T00:00:00Z",
                                author = "octocat",
                                repositoryName = "square/okhttp",
                            ),
                    ),
                )
            cachedIssueRepoImpl.insertIssues(true, "cursor1", issueDTO, newIssues)
            val pagingSource = db.issueDao().getRepositoryIssues("square/okhttp")
            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            val page = result as PagingSource.LoadResult.Page
            Truth.assertThat(page.data.size).isEqualTo(1)
            Truth.assertThat(page.data[0].issueId).isEqualTo("issue_id_2")
        }

    @Test
    fun insertIssues_withoutRefresh_shouldOnlyInsertNewData() =
        runTest {
            // Prepare test data
            val issueDTO =
                CachedIssueDTO(
                    repository = "square/okhttp",
                    assignee = listOf("square/okhttp"),
                    labels = listOf("bug", "network"),
                    issueStatus = "open",
                    query = "okhttp",
                )
            val initialIssues =
                listOf(
                    IssueWithAssigneesAndLabels(
                        assignees = listOf(),
                        labels = listOf(),
                        issue =
                            CachedIssueEntity(
                                issueId = "issue_id_1",
                                bodyText = "Initial body 1",
                                state = "open",
                                url = "https://github.com/square/okhttp/issues/1",
                                title = "Initial title 1",
                                createdAt = "2023-09-20T00:00:00Z",
                                author = "octocat",
                                repositoryName = "square/okhttp",
                            ),
                    ),
                )

            // Insert initial data
            cachedIssueRepoImpl.insertIssues(false, "cursor1", issueDTO, initialIssues)

            // Insert new data without refresh
            val newIssues =
                listOf(
                    IssueWithAssigneesAndLabels(
                        assignees = listOf(),
                        labels = listOf(),
                        issue =
                            CachedIssueEntity(
                                issueId = "issue_id_2",
                                bodyText = "New body 2",
                                state = "open",
                                url = "https://github.com/square/okhttp/issues/2",
                                title = "New title 2",
                                createdAt = "2023-09-21T00:00:00Z",
                                author = "octocat",
                                repositoryName = "square/okhttp",
                            ),
                    ),
                )
            cachedIssueRepoImpl.insertIssues(false, "cursor2", issueDTO, newIssues)

            // Verify
            val pagingSource = db.issueDao().getRepositoryIssues("square/okhttp")
            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                )

            Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            val page = result as PagingSource.LoadResult.Page
            Truth.assertThat(page.data.size).isEqualTo(2)
        }

    @Test
    fun getIssueByRepository_shouldReturnCorrectIssues() =
        runTest {
            // Prepare test data
            val issueDTO =
                CachedIssueDTO(
                    repository = "square/okhttp",
                    assignee = listOf("ndiritumichael"),
                    labels = listOf("bug", "network"),
                    query = "okhttp",
                    issueStatus = "open",
                )
            val issues =
                listOf(
                    IssueWithAssigneesAndLabels(
                        labels =
                            listOf(
                                LabelEntity(
                                    issueId = "issue_id_1",
                                    label = "bug",
                                    color = "#FF0000",
                                ),
                                LabelEntity(
                                    issueId = "issue_id_1",
                                    label = "network",
                                    color = "#00FF00",
                                ),
                            ),
                        assignees =
                            listOf(
                                AssigneeEntity(
                                    issueId = "issue_id_1",
                                    assignee = "ndiritumichael",
                                ),
                            ),
                        issue =

                            CachedIssueEntity(
                                issueId = "issue_id_1",
                                bodyText = "Body 1",
                                state = "open",
                                url = "https://github.com/square/okhttp/issues/1",
                                title = "okhttp doesn't work in android 5",
                                createdAt = "2023-09-20T00:00:00Z",
                                author = "octocat",
                                repositoryName = "square/okhttp",
                            ),
                    ),
                    IssueWithAssigneesAndLabels(
                        labels =
                            listOf(
                                LabelEntity(
                                    issueId = "issue_id_2",
                                    label = "bug",
                                    color = "#FF0000",
                                ),
                                LabelEntity(
                                    issueId = "issue_id_2",
                                    label = "network",
                                    color = "#00FF00",
                                ),
                            ),
                        assignees =
                            listOf(
                                AssigneeEntity(
                                    issueId = "issue_id_2",
                                    assignee = "ndiritumichael",
                                ),
                            ),
                        issue =

                            CachedIssueEntity(
                                issueId = "issue_id_2",
                                bodyText = "Body 2",
                                state = "open",
                                url = "https://github.com/square/okhttp/issues/2",
                                title = "Title 2",
                                createdAt = "2023-09-21T00:00:00Z",
                                author = "octocat",
                                repositoryName = "square/okhttp",
                            ),
                    ),
                    IssueWithAssigneesAndLabels(
                        labels =
                            listOf(
                                LabelEntity(
                                    issueId = "issue_id_3",
                                    label = "bug",
                                    color = "#FF0000",
                                ),
                                LabelEntity(
                                    issueId = "issue_id_3",
                                    label = "network",
                                    color = "#00FF00",
                                ),
                            ),
                        assignees =
                            listOf(
                                AssigneeEntity(
                                    issueId = "issue_id_3",
                                    assignee = "ndiritumichael",
                                ),
                            ),
                        issue =

                            CachedIssueEntity(
                                issueId = "issue_id_3",
                                bodyText = "Coil body",
                                state = "open",
                                url = "https://github.com/coil-kt/coil/issues/3",
                                title = "Coil title",
                                createdAt = "2023-09-22T00:00:00Z",
                                author = "coil-kt",
                                repositoryName = "coil/coil",
                            ),
                    ),
                )

            // Insert data
            cachedIssueRepoImpl.insertIssues(false, "cursor1", issueDTO, issues)

            // Query
            val pagingSource =
                cachedIssueRepoImpl.getIssueByRepository(
                    "square/okhttp",
                    null,
                    null,
                    "okhttp",
                    "open",
                )
            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 10,
                        placeholdersEnabled = false,
                    ),
                )

            // Verify
            Truth.assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
            val page = result as PagingSource.LoadResult.Page

            Truth.assertThat(page.data.size).isEqualTo(1)
            Truth.assertThat(page.data[0].issue.issueId).isEqualTo("issue_id_1")
        }

    @Test
    fun remoteKeyByQuery_shouldReturnCorrectKey() =
        runTest {
            val issueDTO =
                CachedIssueDTO(
                    repository = "square/okhttp",
                    assignee = listOf("ndiritumichael"),
                    labels = listOf("bug", "network"),
                    query = "okhttp",
                    issueStatus = "open",
                )
            val issues =
                listOf(
                    IssueWithAssigneesAndLabels(
                        labels =
                            listOf(
                                LabelEntity(
                                    issueId = "issue_id_1",
                                    label = "bug",
                                    color = "#FF0000",
                                ),
                                LabelEntity(
                                    issueId = "issue_id_1",
                                    label = "network",
                                    color = "#00FF00",
                                ),
                            ),
                        assignees =
                            listOf(
                                AssigneeEntity(
                                    issueId = "issue_id_1",
                                    assignee = "ndiritumichael",
                                ),
                            ),
                        issue =

                            CachedIssueEntity(
                                issueId = "issue_id_1",
                                bodyText = "Body 1",
                                state = "open",
                                url = "https://github.com/square/okhttp/issues/1",
                                title = "Title 1",
                                createdAt = "2023-09-20T00:00:00Z",
                                author = "octocat",
                                repositoryName = "square/okhttp",
                            ),
                    ),
                )

            // Insert data
            cachedIssueRepoImpl.insertIssues(false, "cursor1", issueDTO, issues)

            // Query
            val remoteKey =
                cachedIssueRepoImpl.remoteKeyByQuery(
                    CachedIssueDTO(
                        assignee = listOf("ndiritumichael"),
                        labels = listOf("bug", "network"),
                        repository = "square/okhttp",
                        sortBy = null,
                        issueStatus = "open",
                        query = "okhttp",
                    ),
                )

            // Verify
            Truth.assertThat(remoteKey).isNotNull()
            Truth.assertThat(remoteKey?.nextCursor).isEqualTo("cursor1")
            Truth.assertThat(remoteKey?.repoName).isEqualTo("square/okhttp")
            Truth.assertThat(remoteKey?.assignee).containsExactly("ndiritumichael")
            Truth.assertThat(remoteKey?.labels).isEqualTo(listOf("bug", "network"))
        }
}
