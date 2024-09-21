package com.devmike.data.mapper

import com.devmike.database.entities.CachedRepository
import com.devmike.domain.models.RepositoryModel
import com.devmike.network.dto.RepositoryDTO

fun RepositoryDTO.toCachedRepository(query: String): CachedRepository =
    CachedRepository(
        url = url,
        name = name,
        nameWithOwner = nameWithOwner,
        owner = owner,
        description = description,
        stargazers = stargazers,
        forkCount = forkCount,
        issueCount = issueCount,
        searchQuery = query,
        avatarUrl = avatarUrl,
    )

fun CachedRepository.toRepositoryModel(): RepositoryModel =
    RepositoryModel(
        url = url,
        name = name,
        nameWithOwner = nameWithOwner,
        owner = owner,
        description = description,
        stargazers = stargazers,
        forkCount = forkCount,
        issueCount = issueCount,
        avatarUrl = avatarUrl,
    )
