package com.devmike.gitissuesmobile.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestinations  {

    @Serializable
    data class Issues(
        val repository: String,
    ) : AppDestinations()

    @Serializable
    data object RepositorySearch : AppDestinations()
}
