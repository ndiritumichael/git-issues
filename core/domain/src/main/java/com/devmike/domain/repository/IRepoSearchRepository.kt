package com.devmike.domain.repository

import androidx.paging.PagingData
import com.devmike.domain.models.RepositoryModel
import kotlinx.coroutines.flow.Flow

interface IRepoSearchRepository {
    fun searchRepositories(query: String): Flow<PagingData<RepositoryModel>>
}
