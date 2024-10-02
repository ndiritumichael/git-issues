package com.devmike.database.fakedata

import com.devmike.database.entities.CachedRepository

val sampleCachedRepositories
    get() = listOf(composeRepo, retrofitRepo, roomRepo)
val composeRepo =
    CachedRepository(
        url = "https://github.com/google/JetpackCompose",
        name = "Jetpack Compose",
        nameWithOwner = "google/JetpackCompose",
        owner = "google",
        description = "Jetpack Compose is Android's modern toolkit for building nativeUI.",
        stargazers = 10000,
        forkCount = 5000,
        issueCount = 200,
        searchQuery = "compose",
        avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
    )
val retrofitRepo =
    CachedRepository(
        url = "https://github.com/square/retrofit",
        name = "Retrofit",
        nameWithOwner = "square/retrofit",
        owner = "square",
        description = "A type-safe HTTP client for Android and Java.",
        stargazers = 40000,
        forkCount = 10000,
        issueCount = 500,
        searchQuery = "retrofit",
        avatarUrl = "https://avatars.githubusercontent.com/u/82592?v=4",
    )

val roomRepo =
    CachedRepository(
        url = "https://github.com/google/room",
        name = "Room",
        nameWithOwner = "google/room",
        owner = "google",
        description = "The AndroidX Jetpack component for persisting data in SQLite.",
        stargazers = 20000,
        forkCount = 7000,
        issueCount = 100,
        searchQuery = "room",
        avatarUrl = "https://avatars.githubusercontent.com/u/1342004?v=4",
    )
