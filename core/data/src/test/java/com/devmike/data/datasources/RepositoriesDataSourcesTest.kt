package com.devmike.data.datasources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.data.mapper.toCachedRepository
import com.devmike.data.sources.repositories.RepositoriesDataSources
import com.devmike.database.entities.CachedRepository
import com.devmike.database.entities.RemoteKeyEntity
import com.devmike.database.repository.CachedRepoSearch
import com.devmike.network.dto.PagedDtoWrapper
import com.devmike.network.dto.RepositoryDTO
import com.devmike.network.networkSource.GitHubIssuesRepo
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class RepositoriesDataSourcesTest {
    private lateinit var githubIssuesRepository: GitHubIssuesRepo
    private lateinit var cachedRepository: CachedRepoSearch
    private lateinit var remoteMediator: RepositoriesDataSources

    @MockK
    private lateinit var pagingState: PagingState<Int, CachedRepository>

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        githubIssuesRepository = mockk()
        cachedRepository = mockk()
        remoteMediator = RepositoriesDataSources("query", githubIssuesRepository, cachedRepository)
    }

    @Test
    fun load_refresh_success() =
        runTest {
            val repoSearchResult =
                PagedDtoWrapper(
                    data =
                        listOf(
                            RepositoryDTO(
                                name = "name1",
                                nameWithOwner = "owner1/name1",
                                description = "desc1",
                                owner = "owner1",
                                avatarUrl = "avatar1",
                                stargazers = 100,
                                forkCount = 10,
                                issueCount = 15,
                                url = "https://github.com/owner1/name1",
                            ),
                        ),
                    nextCursor = "cursor2",
                    hasNextPage = true,
                )
            coEvery { githubIssuesRepository.searchRepositories("query", null) } returns
                Result.success(
                    repoSearchResult,
                )
            coEvery { cachedRepository.insertRepositories(any(), any(), any(), any()) } just runs

            val result = remoteMediator.load(LoadType.REFRESH, pagingState)

            coVerify { githubIssuesRepository.searchRepositories("query", null) }
            coVerify {
                cachedRepository.insertRepositories(
                    true,
                    "query",
                    "cursor2",
                    repoSearchResult.data.map { it.toCachedRepository("query") },
                )
            }
            Truth.assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            val successResult = result as RemoteMediator.MediatorResult.Success
            Truth.assertThat(successResult.endOfPaginationReached).isFalse()
        }

    @Test
    fun load_refresh_error() =
        runTest {
            val exception = Exception("Network error")
            coEvery { githubIssuesRepository.searchRepositories("query", null) } returns
                Result.failure(
                    exception,
                )

            val result = remoteMediator.load(LoadType.REFRESH, pagingState)

            coVerify { githubIssuesRepository.searchRepositories("query", null) }
            Truth.assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
            val errorResult = result as RemoteMediator.MediatorResult.Error
            Truth.assertThat(errorResult.throwable).isEqualTo(exception)
        }

    @Test
    fun load_prepend_success() =
        runTest {
            val result = remoteMediator.load(LoadType.PREPEND, pagingState)

            Truth.assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            val successResult = result as RemoteMediator.MediatorResult.Success
            Truth.assertThat(successResult.endOfPaginationReached).isTrue()
        }

    @Test
    fun load_append_success() =
        runTest {
            val remoteKey = RemoteKeyEntity("query", "cursor2")
            coEvery { cachedRepository.remoteKeyByQuery("query") } returns remoteKey
            val repoSearchResult =
                PagedDtoWrapper(
                    data =
                        listOf(
                            RepositoryDTO(
                                name = "name1",
                                nameWithOwner = "owner1/name1",
                                description = "desc1",
                                owner = "owner1",
                                avatarUrl = "avatar1",
                                stargazers = 100,
                                forkCount = 10,
                                issueCount = 15,
                                url = "https://github.com/owner1/name1",
                            ),
                        ),
                    nextCursor = "cursor3",
                    hasNextPage = false,
                )
            coEvery { githubIssuesRepository.searchRepositories("query", "cursor2") } returns
                Result.success(
                    repoSearchResult,
                )
            coEvery { cachedRepository.insertRepositories(any(), any(), any(), any()) } just runs

            val result = remoteMediator.load(LoadType.APPEND, pagingState)

            coVerify { cachedRepository.remoteKeyByQuery("query") }
            coVerify { githubIssuesRepository.searchRepositories("query", "cursor2") }
            coVerify {
                cachedRepository.insertRepositories(
                    false,
                    "query",
                    "cursor3",
                    repoSearchResult.data.map { it.toCachedRepository("query") },
                )
            }
            Truth.assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Success::class.java)
            val successResult = result as RemoteMediator.MediatorResult.Success
            Truth.assertThat(successResult.endOfPaginationReached).isTrue()
        }

    @Test
    fun load_append_error() =
        runTest {
            val remoteKey = RemoteKeyEntity("query", "cursor2")
            coEvery { cachedRepository.remoteKeyByQuery("query") } returns remoteKey
            val exception = Exception("Network error")
            coEvery { githubIssuesRepository.searchRepositories("query", "cursor2") } returns
                Result.failure(
                    exception,
                )

            val result = remoteMediator.load(LoadType.APPEND, pagingState)

            coVerify { cachedRepository.remoteKeyByQuery("query") }
            coVerify { githubIssuesRepository.searchRepositories("query", "cursor2") }
            Truth.assertThat(result).isInstanceOf(RemoteMediator.MediatorResult.Error::class.java)
            val errorResult = result as RemoteMediator.MediatorResult.Error
            Truth.assertThat(errorResult.throwable).isEqualTo(exception)
        }
}
