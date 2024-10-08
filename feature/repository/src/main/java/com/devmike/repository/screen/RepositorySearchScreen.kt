package com.devmike.repository.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.devmike.repository.screen.components.IdleScreen
import com.devmike.repository.screen.components.LogoutConfirmationDialog
import com.devmike.repository.screen.components.RepositoryListScreen
import com.devmike.repository.screen.components.SearchTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositorySearchScreen(
    viewModel: RepositorySearchViewModel = hiltViewModel(),
    onLogoutClicked: () -> Unit,
    onRepositoryClick: (name: String, owner: String) -> Unit,
) {
    LaunchedEffect(viewModel) {
        viewModel.modifyDebounceTime(DEBOUNCE_DURATION)
    }
    val repositoriesState = viewModel.searchResults.collectAsLazyPagingItems()

    val showIdleScreen =
        remember(viewModel.searchQuery) {
            derivedStateOf {
                viewModel.searchQuery.length < MINIMUM_SEARCH_LENGTH
            }
        }.value

    var showLogoutDialog by remember { mutableStateOf(false) }

    LogoutConfirmationDialog(
        showDialog = showLogoutDialog,
        onDismiss = { showLogoutDialog = false },
        onLogout = onLogoutClicked,
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets(top = 0.dp, bottom = 0.dp),
                title = {
                    SearchTextField(
                        searchText = viewModel.searchQuery,
                        onSearchTextChanged = viewModel::modifySearchQuery,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        label = "all repositories",
                    )
                },
                actions = {
                    IconButton(
                        onClick = { showLogoutDialog = true },
                        modifier = Modifier.testTag("logout_button"),
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "logout",
                        )
                    }
                },
            )
        },
    ) { paddingValues ->

        AnimatedContent(targetState = showIdleScreen, label = "showidlescreen") { show ->

            if (show) {
                IdleScreen(modifier = Modifier.padding(paddingValues))
            } else {
                RepositoryListScreen(
                    modifier = Modifier.padding(paddingValues).testTag("repositorieslist"),
                    repositoriesState = repositoriesState,
                    onRepositoryClick = onRepositoryClick,
                )
            }
        }
    }
}

const val MINIMUM_SEARCH_LENGTH = 3
const val DEBOUNCE_DURATION = 500L
