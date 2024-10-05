package com.devmike.issues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.testing.invoke
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.cachedIn
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.devmike.domain.appdestinations.AppDestinations
import com.devmike.issues.screen.IssuesViewModel
import com.devmike.issues.testdouble.IssueRepositoryDouble
import com.devmike.issues.testdouble.fakeFlutterAssignees
import com.devmike.issues.testdouble.fakeFlutterIssues
import com.devmike.issues.testdouble.fakeFlutterLabels
import com.devmike.issues.util.IssueDiffCallback
import com.devmike.issues.util.IssueItemUpdateCallback
import com.devmike.issues.util.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class IssueViewModelTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    lateinit var viewModel: IssuesViewModel

    @Before
    fun setup() =
        runBlocking {
            val issuesRepository = IssueRepositoryDouble()
            val repoDetails: AppDestinations.Issues = AppDestinations.Issues("flutter", "flutter")
            val savedStateHandle = SavedStateHandle.Companion.invoke(repoDetails)
            viewModel = IssuesViewModel(savedStateHandle, issuesRepository)
        }

    @Test
    fun `verify initial state - searchQuery is empty`() {
        Truth.assertThat(viewModel.searchQuery).isEqualTo("")
    }

    @Test
    fun `verify navigation variables are decoded correctly`() {
        Truth.assertThat(viewModel.repoDetails.owner).isEqualTo("flutter")
        Truth.assertThat(viewModel.repoDetails.repository).isEqualTo("flutter")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `issuesResults emits correct data when filters are applied`() =
        runTest {
            val scope = CoroutineScope(Dispatchers.Unconfined)

            val job =
                backgroundScope.launch {
                    viewModel.issuesResults.collect()
                }

            val differ =
                AsyncPagingDataDiffer(
                    diffCallback = IssueDiffCallback(),
                    updateCallback = IssueItemUpdateCallback(),
                )

            advanceUntilIdle()

            viewModel.issuesResults.test {
                // val first = awaitItem()

                val data = awaitItem()

                data
                    .cachedIn(
                        scope,
                    ).test {
                        val items = awaitItem()

                        differ.submitData(items)

                        Truth.assertThat(differ.snapshot()).containsExactlyElementsIn(
                            fakeFlutterIssues,
                        )

                        //  awaitComplete()
                    }
            }

            job.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `repositoryLabels emits correct data`() =
        runTest {
            val scope = CoroutineScope(Dispatchers.Unconfined)

            val job =
                backgroundScope.launch {
                    viewModel.repositoryLabels.collect()
                }

            val differ =
                AsyncPagingDataDiffer(
                    diffCallback = com.devmike.issues.util.LabelDiffCallback,
                    updateCallback = IssueItemUpdateCallback(),
                )

            advanceUntilIdle()

            viewModel.repositoryLabels
                .test {
                    val items = awaitItem()

                    differ.submitData(items)

                    Truth.assertThat(differ.snapshot()).containsExactlyElementsIn(fakeFlutterLabels)

                    // awaitComplete()
                }

            job.cancel()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `repositoryAssignees emitscorrect data`() =
        runTest {
            val scope = CoroutineScope(Dispatchers.Unconfined)

            val job =
                backgroundScope.launch {
                    viewModel.repositoryAssignees.collect()
                }

            val differ =
                AsyncPagingDataDiffer(
                    diffCallback = com.devmike.issues.util.AssigneeDiffCallback,
                    updateCallback = IssueItemUpdateCallback(),
                )

            advanceUntilIdle()

            viewModel.repositoryAssignees.test {
                val items = awaitItem()

                differ.submitData(items)

                Truth.assertThat(differ.snapshot()).containsExactlyElementsIn(fakeFlutterAssignees)

                // awaitComplete()
            }

            job.cancel()
        }
}
