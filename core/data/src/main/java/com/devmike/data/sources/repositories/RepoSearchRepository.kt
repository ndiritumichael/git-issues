package com.devmike.data.sources.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.devmike.data.mapper.toRepositoryModel
import com.devmike.database.repository.CachedRepoSearch
import com.devmike.domain.models.RepositoryModel
import com.devmike.domain.repository.IRepoSearchRepository
import com.devmike.network.networkSource.GitHubIssuesRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepoSearchRepository
    @Inject
    constructor(
        private val githubIssuesRepo: GitHubIssuesRepo,
        private val cachedRepoSearch: CachedRepoSearch,
    ) : IRepoSearchRepository {
        @OptIn(ExperimentalPagingApi::class)
        override fun searchRepositories(query: String): Flow<PagingData<RepositoryModel>> {
            val pagingSourceFactory = { cachedRepoSearch.searchRepositories(query) }

            return Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false),
                remoteMediator =
                    RepositoriesDataSources(
                        query,
                        githubIssuesRepo,
                        cachedRepoSearch,
                    ),
                pagingSourceFactory = pagingSourceFactory,
            ).flow
                .map { pagingData ->
                    pagingData.map {
                        it.toRepositoryModel()
                    }
                }
        }
    }
