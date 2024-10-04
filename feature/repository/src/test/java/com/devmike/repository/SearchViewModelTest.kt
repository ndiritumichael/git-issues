package com.devmike.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.domain.models.RepositoryModel
import com.devmike.domain.repository.IRepoSearchRepository
import com.devmike.repository.screen.RepositorySearchViewModel
import com.google.common.truth.Truth
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.times

@RunWith(
    AndroidJUnit4::class,
)
class SearchViewModelTest {
    // Mock dependencies
    @MockK
    private lateinit var repoSearchRepository: IRepoSearchRepository

    // Initialize ViewModel
    private lateinit var viewModel: RepositorySearchViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = MainCoroutineRule()

    @Before
    fun setUp() {
        // Initialize MockK
        MockKAnnotations.init(this, relaxed = true)

        // Initialize ViewModel with mocked repository
        viewModel = RepositorySearchViewModel(repoSearchRepository)
    }

    @Test
    fun `verify initial state - searchQuery is empty`() {
        // Ensure initial search query is empty
        Truth.assertThat(viewModel.searchQuery).isEqualTo("")
    }

    @Test
    fun `modify search query updates the state`() =
        runTest {
            // Given a query string
            val query = "Android"

            // When search query is modified
            viewModel.modifySearchQuery(query)

            // Then searchQuery should be updated
            Truth.assertThat(viewModel.searchQuery).isEqualTo(query)
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `debounce should work when modifying search query quickly`() =
        runTest {
            val mockPagingData = PagingData.from(listOf<RepositoryModel>())
            every { repoSearchRepository.searchRepositories(any()) } returns flowOf(mockPagingData)
            val queries = listOf("A", "An", "Andr", "Android")
            queries.forEach { query ->
                viewModel.modifySearchQuery(query)
            }

            queries.forEach { query ->
                viewModel.modifySearchQuery(query)
            }

            val job =
                launch {
                    viewModel.searchResults.collect {
                    }
                }

            advanceUntilIdle()

            verify(exactly = 1) { repoSearchRepository.searchRepositories(any()) }

            job.cancel()
        }
}
