package com.devmike.domain.appdestinations

import kotlinx.serialization.Serializable

@Serializable
sealed class AppDestinations {
    @Serializable
    data class Issues(
        val repository: String,
        val owner: String,
    ) : AppDestinations()

    @Serializable
    data object RepositorySearch : AppDestinations()
}
