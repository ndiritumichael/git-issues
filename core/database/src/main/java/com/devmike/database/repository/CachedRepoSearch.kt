package com.devmike.database.repository

import androidx.paging.PagingSource
import com.devmike.database.entities.CachedRepository
import com.devmike.database.entities.RemoteKeyEntity

interface CachedRepoSearch {
    suspend fun insertRepositories(
        isRefresh: Boolean,
        query: String,
        nextCursor: String?,
        repositories: List<CachedRepository>,
    )

    fun searchRepositories(query: String): PagingSource<Int, CachedRepository>

    suspend fun deleteRepositories(query: String)

    suspend fun remoteKeyByQuery(query: String): RemoteKeyEntity?
}
