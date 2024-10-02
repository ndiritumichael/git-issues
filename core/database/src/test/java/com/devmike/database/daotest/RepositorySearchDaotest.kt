package com.devmike.database.daotest

import androidx.paging.PagingSource
import com.devmike.database.dao.RepositorySearchDAO
import com.devmike.database.fakedata.composeRepo
import com.devmike.database.fakedata.retrofitRepo
import com.devmike.database.helpers.BaseDbTest
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class RepositorySearchDaotest : BaseDbTest() {
    private lateinit var repoDao: RepositorySearchDAO

    @Before
    fun setUp() {
        repoDao = db.getRepositoryDao()
    }

    @Test
    fun testInsertAndRetrieveRepositories() =
        runTest {
            val query = "retrofit"

            repoDao.insertAll(listOf(retrofitRepo))

            val pagingSource = repoDao.searchRepositories(query)
            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            val page = result as PagingSource.LoadResult.Page
            assertEquals(listOf(retrofitRepo), page.data)
        }

    @Test
    fun testDeleteRepositories() =
        runTest {
            val query = "compose"
            repoDao.insertAll(
                listOf(composeRepo),
            )
            val pagingSource = repoDao.searchRepositories(query)
            val result =
                pagingSource.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                )

            val page = result as PagingSource.LoadResult.Page
            assertEquals(listOf(composeRepo), page.data)
            repoDao.deleteRepositories(query)

            val deletedResults = repoDao.searchRepositories(query)
            val deletedPage =
                deletedResults.load(
                    PagingSource.LoadParams.Refresh(
                        key = null,
                        loadSize = 2,
                        placeholdersEnabled = false,
                    ),
                ) as PagingSource.LoadResult.Page

            Truth.assertThat(deletedPage.data).isEmpty()
        }

    @Test
    fun testInsertDuplicateRepositories() =
        runTest {
            val repo1 = composeRepo
            val updatedRepo1 = repo1.copy(stargazers = 12000)

            repoDao.insertAll(listOf(repo1))
            repoDao.insertAll(listOf(updatedRepo1))

            val pagingSource = repoDao.searchRepositories("compose")
            val loadResult = pagingSource.load(PagingSource.LoadParams.Refresh(null, 2, false))

            Truth.assertThat(loadResult is PagingSource.LoadResult.Page).isTrue()
            loadResult as PagingSource.LoadResult.Page
            Truth.assertThat(loadResult.data.size).isEqualTo(1)
            Truth.assertThat(loadResult.data[0].stargazers).isEqualTo(12000)
        }

    @Test
    fun testSearchRepositoriesWithNoResults() =
        runTest {
            val pagingSource = repoDao.searchRepositories("nonexistent")
            val loadResult = pagingSource.load(PagingSource.LoadParams.Refresh(null, 10, false))

            Truth.assertThat(loadResult is PagingSource.LoadResult.Page).isTrue()
            loadResult as PagingSource.LoadResult.Page
            Truth.assertThat(loadResult.data.isEmpty())
        }
}
