package com.devmike.database.repositories

import androidx.paging.PagingSource
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.database.entities.CachedRepository
import com.devmike.database.helpers.BaseDbTest
import com.devmike.database.repository.CachedRepoSearchImpl
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class CachedRepoSearchImplTest : BaseDbTest() {
    private lateinit var cachedRepoSearchImpl: CachedRepoSearchImpl

    @Before
    fun setUp() {
        cachedRepoSearchImpl = CachedRepoSearchImpl(db)
    }

    @Test
    fun insertRepositories_withRefresh_shouldClearAndInsertNewData() =
        runTest {
            val query = "Jetpack Compose"
            val initialRepos =
                listOf(
                    CachedRepository(
                        url = "https://github.com/google/JetpackCompose",
                        name = "Jetpack Compose",
                        nameWithOwner = "google/JetpackCompose",
                        owner = "google",
                        description =
                            "Jetpack Compose is Android's modern" +
                                " toolkit for building native UI.",
                        stargazers = 10000,
                        forkCount = 5000,
                        issueCount = 200,
                        searchQuery = query,
                        avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                    ),
                )
            cachedRepoSearchImpl.insertRepositories(false, query, "cursor1", initialRepos)

            val newRepos =
                listOf(
                    CachedRepository(
                        url = "https://github.com/square/retrofit",
                        name = "Retrofit",
                        nameWithOwner = "square/retrofit",
                        owner = "square",
                        description = "A type-safe HTTP client for Android and Java.",
                        stargazers = 40000,
                        forkCount = 10000,
                        issueCount = 500,
                        searchQuery = query,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                    ),
                )
            cachedRepoSearchImpl.insertRepositories(true, query, "cursor2", newRepos)

            val pagingSource = cachedRepoSearchImpl.searchRepositories(query)
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
            Truth.assertThat(page.data[0].name).isEqualTo("Retrofit")

            val remoteKey = cachedRepoSearchImpl.remoteKeyByQuery(query)
            Truth.assertThat(remoteKey).isNotNull()
            Truth.assertThat(remoteKey?.nextCursor).isEqualTo("cursor2")
        }

    @Test
    fun insertRepositories_withoutRefresh_shouldOnlyInsertNewData() =
        runTest {
            val query = "Jetpack Compose"
            val initialRepos =
                listOf(
                    CachedRepository(
                        url = "https://github.com/google/JetpackCompose",
                        name = "Jetpack Compose",
                        nameWithOwner = "google/JetpackCompose",
                        owner = "google",
                        description =
                            """
                            Jetpack Compose is Android's modern
                            toolkit for building native
                             UI.
                            """.trimIndent(),
                        stargazers = 10000,
                        forkCount = 5000,
                        issueCount = 200,
                        searchQuery = query,
                        avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                    ),
                )
            cachedRepoSearchImpl.insertRepositories(false, query, "cursor1", initialRepos)

            val newRepos =
                listOf(
                    CachedRepository(
                        url = "https://github.com/square/retrofit",
                        name = "Retrofit",
                        nameWithOwner = "square/retrofit",
                        owner = "square",
                        description = "A type-safe HTTP client for Android and Java.",
                        stargazers = 40000,
                        forkCount = 10000,
                        issueCount = 500,
                        searchQuery = query,
                        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
                    ),
                )
            cachedRepoSearchImpl.insertRepositories(false, query, "cursor2", newRepos)

            val pagingSource = cachedRepoSearchImpl.searchRepositories(query)
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
    fun searchRepositories_shouldReturnCorrectRepositories() =
        runTest {
            val query = "Jetpack Compose"
            val repos =
                listOf(
                    CachedRepository(
                        url = "https://github.com/google/JetpackCompose",
                        name = "Jetpack Compose",
                        nameWithOwner = "google/JetpackCompose",
                        owner = "google",
                        description =
                            """Jetpack Compose
                            |is Android's modern toolkit
                            |for building native UI.
                            """.trimMargin(),
                        stargazers = 10000,
                        forkCount = 5000,
                        issueCount = 200,
                        searchQuery = query,
                        avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                    ),
                )
            cachedRepoSearchImpl.insertRepositories(false, query, "cursor1", repos)

            val pagingSource = cachedRepoSearchImpl.searchRepositories(query)
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
            Truth.assertThat(page.data).isEqualTo(repos)
        }

    @Test
    fun deleteRepositories_shouldRemoveRepositoriesForQuery() =
        runTest {
            val query = "Jetpack Compose"
            val repos =
                listOf(
                    CachedRepository(
                        url = "https://github.com/google/JetpackCompose",
                        name = "Jetpack Compose",
                        nameWithOwner = "google/JetpackCompose",
                        owner = "google",
                        description =
                            """Jetpack Compose is
                            |Android's modern toolkit for
                            | building native UI.
                            """.trimMargin(),
                        stargazers = 10000,
                        forkCount = 5000,
                        issueCount = 200,
                        searchQuery = query,
                        avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                    ),
                )
            cachedRepoSearchImpl.insertRepositories(false, query, "cursor1", repos)

            cachedRepoSearchImpl.deleteRepositories(query)

            val pagingSource = cachedRepoSearchImpl.searchRepositories(query)
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
            Truth.assertThat(page.data).isEmpty()
        }

    @Test
    fun remoteKeyByQuery_shouldReturnCorrectKey() =
        runTest {
            val query = "Jetpack Compose"
            val repos =
                listOf(
                    CachedRepository(
                        url = "https://github.com/google/JetpackCompose",
                        name = "Jetpack Compose",
                        nameWithOwner = "google/JetpackCompose",
                        owner = "google",
                        description =
                            """Jetpack Compose is
                            | Android's modern toolkit for
                            |  building native UI.
                            """.trimMargin(),
                        stargazers = 10000,
                        forkCount = 5000,
                        issueCount = 200,
                        searchQuery = query,
                        avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
                    ),
                )
            cachedRepoSearchImpl.insertRepositories(false, query, "cursor1", repos)

            val remoteKey = cachedRepoSearchImpl.remoteKeyByQuery(query)

            Truth.assertThat(remoteKey).isNotNull()
            Truth.assertThat(remoteKey?.nextCursor).isEqualTo("cursor1")
            Truth.assertThat(remoteKey?.searchQuery).isEqualTo(query)
        }
}
