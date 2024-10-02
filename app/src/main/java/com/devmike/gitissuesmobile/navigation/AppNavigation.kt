package com.devmike.gitissuesmobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.devmike.issues.screen.IssuesScreen
import com.devmike.repository.screen.RepositorySearchScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    onLogout: () -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = com.devmike.domain.appdestinations.AppDestinations.RepositorySearch,
    ) {
        composable<com.devmike.domain.appdestinations.AppDestinations.RepositorySearch> {
            RepositorySearchScreen(onLogoutClicked = onLogout) { name, owner ->
                val destination =
                    com.devmike.domain.appdestinations.AppDestinations
                        .Issues(repository = name, owner = owner)
                navController.navigate(destination)
            }
        }

        composable<com.devmike.domain.appdestinations.AppDestinations.Issues> {
            IssuesScreen {
                navController.navigateUp()
            }
        }
    }
}
