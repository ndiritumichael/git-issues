package com.devmike.repository.uitest

import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.devmike.domain.repository.IRepoSearchRepository
import com.devmike.repository.screen.RepositorySearchScreen
import com.devmike.repository.screen.RepositorySearchViewModel
import com.devmike.repository.screen.components.RepositoryListScreen
import com.devmike.repository.testdouble.RepositorySearchDouble
import com.devmike.repository.testdouble.fakeRustRepositories
import com.devmike.repository.utils.createTestPagingFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositorySearchTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    lateinit var viewModel: RepositorySearchViewModel

    @Before
    fun setup() {
        val fakeRepo: IRepoSearchRepository = RepositorySearchDouble()
        viewModel = RepositorySearchViewModel(fakeRepo)
    }

    @Test
    fun `Idle Screen Is Visible when no search query is provided`() {
        composeTestRule.setContent {
            RepositorySearchScreen(viewModel, {}, { _, _ -> })
        }

        composeTestRule.onNodeWithText("Type at least 3 characters to search").assertExists()
    }

    @Test
    fun `Search Screen Is Visible when search query is provided`() {
        viewModel.modifySearchQuery("Android")

        composeTestRule.setContent {
            RepositorySearchScreen(viewModel, {}, { _, _ -> })
        }

        composeTestRule.onNodeWithTag("repositorieslist").assertExists()
    }

    @Test
    fun repositoryItemsSreVisibleWhenPagingItemsAreProvided() {
        val items = createTestPagingFlow(fakeRustRepositories)

        composeTestRule.setContent {
            RepositoryListScreen(
                modifier = Modifier,
                repositoriesState = items.collectAsLazyPagingItems(),
            ) { _, _ ->
            }
        }

        composeTestRule
            .onAllNodesWithTag(
                "repository_item",
            ).assertAny(hasText("rust", substring = true))
    }

    @Test
    fun `Tapping on logout icon shows the logout dialog`() {
        composeTestRule.setContent {
            RepositorySearchScreen(viewModel, {}, { _, _ -> })
        }

        composeTestRule.onNodeWithTag("logout_button").performClick()

        composeTestRule.onNodeWithTag("Logout_Dialog").assertExists()

        composeTestRule.onNodeWithText("Logout Confirmation").assertExists()
    }

    @Test
    fun itemsaredisplayedforacorrectsearchquery() {
        //  viewModel.modifySearchQuery("flutter")
        composeTestRule.setContent {
            RepositorySearchScreen(viewModel, {}, { _, _ -> })
        }
        composeTestRule.onNodeWithTag("search_repositories").performTextInput("flutter")
        // Allow time for debounce and collection
        composeTestRule.mainClock.advanceTimeBy(500)

        composeTestRule
            .onAllNodesWithTag(
                "repository_item",
            ).assertAny(hasText("flutter", substring = true))
    }
}
