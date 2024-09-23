package com.devmike.gitissuesmobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.devmike.issues.screen.IssuesScreen
import com.devmike.repository.screen.RepositorySearchScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppDestinations.RepositorySearch) {
        composable<AppDestinations.RepositorySearch> {
            RepositorySearchScreen {
                val destination = AppDestinations.Issues(it)
                navController.navigate(destination)
            }
        }

        composable<AppDestinations.Issues> {
            val repository: AppDestinations.Issues = it.toRoute()
            IssuesScreen(repository = repository.repository) {
                navController.navigateUp()
            }
        }
    }
}
