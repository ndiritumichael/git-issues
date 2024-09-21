package com.devmike.database.repository

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.devmike.database.GithubDatabase
import com.devmike.database.entities.CachedRepository
import com.devmike.database.entities.RemoteKeyEntity
import javax.inject.Inject

/**
 * This class implements the CachedRepoSearch interface and provides methods for interacting with the
 * cached repositories in the GithubDatabase. It uses the RepositorySearchDAO and RemoteKeyDAO to perform
 * database operations without having to expose the db and dao to the data layer
 *
 * @param db The GithubDatabase instance used for database operations.
 */

class CachedRepoSearchImpl
    @Inject
    constructor(
        private val db: GithubDatabase,
    ) : CachedRepoSearch {
        private val cacheRepositoryDAO = db.getRepositoryDao()
        private val repoKeysDao = db.remoteKeyDao()

        override suspend fun insertRepositories(
            isRefresh: Boolean,
            query: String,
            nextCursor: String?,
            repositories: List<CachedRepository>,
        ) {
            db.withTransaction {
                if (isRefresh) {
                    cacheRepositoryDAO.deleteRepositories(query)
                    repoKeysDao.clearRemoteKeysForQuery(query)
                }

                repoKeysDao.insertRemoteKey(RemoteKeyEntity(query, nextCursor))
                cacheRepositoryDAO.insertAll(repositories)
            }
        }

        override fun searchRepositories(query: String): PagingSource<Int, CachedRepository> =
            cacheRepositoryDAO.searchRepositories(query)

        override suspend fun deleteRepositories(query: String) {
            cacheRepositoryDAO.deleteRepositories(query)
        }

        override suspend fun remoteKeyByQuery(query: String): RemoteKeyEntity? =
            repoKeysDao.remoteKeyByQuery(query)
    }
