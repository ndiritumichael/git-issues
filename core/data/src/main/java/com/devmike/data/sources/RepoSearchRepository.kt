package com.devmike.data.sources

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.devmike.database.GithubDatabase
import com.devmike.database.entities.CachedRepository
import com.devmike.network.networkSource.GitHubIssuesRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoSearchRepository
    @Inject
    constructor(
        private val githubIssuesRepo: GitHubIssuesRepo,
        private val db: GithubDatabase,
    ) {
        @OptIn(ExperimentalPagingApi::class)
        fun searchRepositories(query: String): Flow<PagingData<CachedRepository>> {
            //   val dbQuery = "%${query.replace(' ', '%')}%"
            val pagingSourceFactory = { db.getRepositoryDao().searchRepositories(query) }

            return Pager(
                config = PagingConfig(pageSize = 10, enablePlaceholders = false),
                remoteMediator =
                    RepositoriesDataSources(
                        query,
                        githubIssuesRepo,
                        db.getRepositoryDao(),
                        db,
                    ),
                pagingSourceFactory = pagingSourceFactory,
            ).flow
            //
            //        .map { pagingData ->
//            pagingData.map {
//                it.toRepositoryModel()
//            }
//        }
        }
    }
