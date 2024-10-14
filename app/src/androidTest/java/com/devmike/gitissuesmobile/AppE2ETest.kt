package com.devmike.gitissuesmobile

import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasContentDescriptionExactly
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.devmike.datastore.repo.DataStoreRepo
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@OptIn(ExperimentalCoroutinesApi::class)
class AppE2ETest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Inject
    lateinit var dataStoreRepo: DataStoreRepo

    @Before
    fun setup() {
        hiltRule.inject()
    }

    /**
     * This test function verifies the login and logout flow in the application.
     * It uses the AndroidComposeRule to interact with the UI components and the DataStoreRepo to simulate user actions.
     *
     * @see androidx.compose.ui.test.junit4.createAndroidComposeRule
     * @see androidx.compose.ui.test.onNodeWithTag
     * @see androidx.compose.ui.test.onNodeWithText
     * @see androidx.compose.ui.test.performClick
     * @see com.devmike.datastore.repo.DataStoreRepo
     *
     * @return Unit - This function does not return any value.
     */

    @Test
    fun login_logout_flow_works() =
        runTest {
            // Wait for the UI to be idle before interacting with it
            composeTestRule.waitForIdle()
            advanceUntilIdle()

            // Verify that the login screen is displayed
            composeTestRule.onNodeWithText("Please login to continue").assertIsDisplayed()
            composeTestRule.onNodeWithText("Login with GitHub").assertIsDisplayed()

            // Simulate a user login by inserting a token into the DataStore
            dataStoreRepo.insertToken("token")

            // Verify that the idle screen is displayed after login
            composeTestRule.onNodeWithTag("idle_screen").assertExists()

            // Simulate a user logout by clicking the logout button
            composeTestRule.onNodeWithTag("logout_button").performClick()

            // Verify that the logout dialog is displayed
            composeTestRule.onNodeWithTag("Logout_Dialog").assertExists()

            // Verify that the logout confirmation message is displayed
            composeTestRule.onNodeWithText("Logout Confirmation").assertExists()

            // Simulate a user confirmation of logout by clicking the confirm logout button
            composeTestRule.onNodeWithTag("confirm_logout").performClick()

            // Verify that the login screen is displayed after logout
            advanceUntilIdle()
            composeTestRule.awaitIdle()

            composeTestRule.onNodeWithText("Please login to continue").assertIsDisplayed()
        }

    @Test
    fun searching_for_valid_repositories_displays_results() =
        runTest {
            // Wait for the UI to be idle before interacting with it
            composeTestRule.waitForIdle()

            // Simulate a user login by inserting a token into the DataStore
            dataStoreRepo.insertToken("token")

            // Verify that the idle screen is displayed after login
            composeTestRule.onNodeWithTag("idle_screen").assertExists()

            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNodeWithTag("search_repositories").performTextInput("flutter")
            advanceUntilIdle()
            composeTestRule
                .onAllNodesWithTag(
                    "repository_item",
                ).assertAny(hasText("flutter", substring = true))
        }

    @Test
    fun searching_for_invalid_repositories_displays_error_message() =
        runTest {
            // Wait for the UI to be idle before interacting with it
            composeTestRule.waitForIdle()

            // Simulate a user login by inserting a token into the DataStore
            dataStoreRepo.insertToken("token")

            advanceUntilIdle()

            // Verify that the idle screen is displayed after login
            composeTestRule.onNodeWithTag("idle_screen").assertExists()

            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNodeWithTag("search_repositories").performTextInput("invalid_query")
            advanceUntilIdle()
            composeTestRule.onNodeWithText("No repositories found").assertIsDisplayed()
        }

    @Test
    fun tapping_on_repository_without_issues_does_not_display_issues_screen() =
        runTest {
            val repowithoutissuesdescription = "Plugins for Flutter maintained by the Flutter team"
            // Wait for the UI to be idle before interacting with it
            composeTestRule.waitForIdle()

            dataStoreRepo.insertToken("token")

            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNodeWithTag("search_repositories").performTextInput("flutter")

            val noIssueItem =
                composeTestRule.onNode(
                    hasAnyChild(hasText(repowithoutissuesdescription)),
                )
            noIssueItem.assertIsDisplayed()
            noIssueItem.performClick()

            noIssueItem.assertIsDisplayed()
        }

    @Test
    fun tapping_on_repository_with_issues_navigates_to_issues_screen() =
        runTest {
            val repowithIssues = "flutter/flutter"

            composeTestRule.waitForIdle()

            // Simulate a user login by inserting a token into the DataStore
            dataStoreRepo.insertToken("token")

            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNodeWithTag("search_repositories").performTextInput("flutter")

            val noIssueItem =
                composeTestRule.onNode(
                    hasText(repowithIssues),
                )
            noIssueItem.assertIsDisplayed()
            noIssueItem.performClick()

            composeTestRule
                .onNode(hasTestTag("issue_app_bar") and hasAnyChild(hasText(repowithIssues)))
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("Issues").assertIsDisplayed()

            //  composeTestRule.onNodeWithText(issueTitle).assertExists()
            // composeTestRule.onNodeWithText(issueTitle).assertIsDisplayed()
        }

    @Test
    fun invoking_back_navigation_gesture_navigates_to_previous_screen() =
        runTest {
            val repoWithIssues = "flutter/flutter"

            composeTestRule.waitForIdle()

            // Simulate a user login by inserting a token into the DataStore
            dataStoreRepo.insertToken("token")

            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNodeWithTag("search_repositories").performTextInput("flutter")

            val noIssueItem = composeTestRule.onNode(hasText(repoWithIssues))
            noIssueItem.assertIsDisplayed()
            noIssueItem.performClick()

            // Verify that the issues screen is displayed
            composeTestRule
                .onNode(hasTestTag("issue_app_bar") and hasAnyChild(hasText(repoWithIssues)))
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("Issues").assertIsDisplayed()

            // Simulate pressing the back button
            composeTestRule.activityRule.scenario.onActivity { activity ->
                activity.onBackPressedDispatcher.onBackPressed()
            }

            // Verify that the repositories screen is displayed again
            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNode(hasText(repoWithIssues)).assertIsDisplayed()
        }

    @Test
    fun tapping_back_button_navigates_to_previous_screen() =
        runTest {
            val repoWithIssues = "flutter/flutter"

            composeTestRule.waitForIdle()

            // Simulate a user login by inserting a token into the DataStore
            dataStoreRepo.insertToken("token")

            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNodeWithTag("search_repositories").performTextInput("flutter")

            val noIssueItem = composeTestRule.onNode(hasText(repoWithIssues))
            noIssueItem.assertIsDisplayed()
            noIssueItem.performClick()

            // Verify that theissues screen is displayed
            composeTestRule
                .onNode(hasTestTag("issue_app_bar") and hasAnyChild(hasText(repoWithIssues)))
                .assertIsDisplayed()
            composeTestRule.onNodeWithText("Issues").assertIsDisplayed()

            composeTestRule
                .onNode(hasAnyChild(hasContentDescriptionExactly("Back")))
                .assertIsDisplayed()
            composeTestRule.onNode(hasContentDescriptionExactly("Back")).performClick()

            // Verify that the repositories screen is displayed again
            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNode(hasText(repoWithIssues)).assertIsDisplayed()
        }

    @Test
    fun navigating_to_issues_screen_shows_issues() =
        runTest(UnconfinedTestDispatcher()) {
            val repowithIssues = "flutter/flutter"
            val issueTitle = "🔥 Crash on startup with null pointer exception"
            composeTestRule.waitForIdle()

            // Simulate a user login by inserting a token into the DataStore
            dataStoreRepo.insertToken("token")
            advanceUntilIdle()

            composeTestRule.onNodeWithTag("search_repositories").assertIsDisplayed()
            composeTestRule.onNodeWithTag("search_repositories").performTextInput("flutter")

            val noIssueItem =
                composeTestRule.onNode(
                    hasText(repowithIssues),
                )
            noIssueItem.assertIsDisplayed()
            noIssueItem.performClick()

            // advanceUntilIdle()

            composeTestRule.awaitIdle()

            composeTestRule
                .onNode(hasTestTag("issue_app_bar") and hasAnyChild(hasText(repowithIssues)))
                .assertIsDisplayed()

            composeTestRule.onNodeWithText("Issues").assertIsDisplayed()
            advanceUntilIdle()

            composeTestRule.awaitIdle()

            composeTestRule.onNodeWithContentDescription("Search").assertIsDisplayed()

            // necessary to keep UI test busy a Items Load,only happens if paging flow is cached in viewmodelscope
            composeTestRule.onNodeWithContentDescription("Search").performClick()
            composeTestRule.waitForIdle()

            composeTestRule.waitUntil(2000) {
                composeTestRule.onNodeWithText(issueTitle).assertExists().isDisplayed()
            }
        }
}
