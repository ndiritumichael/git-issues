package com.devmike.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.devmike.database.entities.CachedRepository

@Dao
interface RepositorySearchDAO {
    @Upsert
    suspend fun upsertAll(repositories: List<CachedRepository>)

    @Query(
        """SELECT * FROM repositories
             WHERE searchQuery = :query
                ORDER BY stargazers DESC,name ASC""",
    )
    fun searchRepositories(query: String): PagingSource<Int, CachedRepository>

    @Query("DELETE FROM repositories WHERE searchQuery = :query")
    suspend fun deleteRepositories(query: String)
}
