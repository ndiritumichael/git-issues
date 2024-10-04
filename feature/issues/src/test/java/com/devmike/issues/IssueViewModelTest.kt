package com.devmike.issues

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.testing.invoke
import androidx.paging.testing.asSnapshot
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.devmike.domain.appdestinations.AppDestinations
import com.devmike.domain.helper.IssueState
import com.devmike.domain.models.IssueSearchModel
import com.devmike.issues.screen.IssuesViewModel
import com.devmike.issues.testdouble.IssueRepositoryDouble
import com.devmike.issues.util.MainCoroutineRule
import com.google.common.truth.Truth
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
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
            advanceUntilIdle()

        /*    backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.issuesResults.collect()
            }*/
            val issmodel =
                IssueSearchModel(
                    repository = "flutter/flutter",
                    labels = null,
                    assignees = null,
                    issueState = IssueState.ALL.state,
                    query = null,
                    sortBy = "created-desc",
                )
            // Launch a coroutine to collect from issuesResults
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) {
                viewModel.issuesResults.collect()
            }

            advanceUntilIdle()

            // println("the results are ${issuesResults.size}")
            // val issuesResults = viewModel.issuesRepository.getPagedIssues(issmodel).asSnapshot { }

            // Truth.assertThat(issuesResults).isEmpty()
//            val data = viewModel.someResults.value?.asSnapshot()
//
//            println(" the data is $data")
            advanceUntilIdle()

            viewModel.someResults.test {
                val flow = awaitItem() // Collect the first (and likely only) item
                println("the flow is ${flow?.asSnapshot()}") // Access the snapshot and print
                awaitComplete() // Ensure the flow completes (if expected)
            }

            // println("the value of the flow is${viewModel.someResults.value?.asSnapshot { }}")

            advanceUntilIdle()
        }
}
