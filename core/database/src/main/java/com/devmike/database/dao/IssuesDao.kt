package com.devmike.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.devmike.database.entities.CachedIssueEntity

@Dao
interface IssuesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIssues(issues: List<CachedIssueEntity>)

    @Query("Delete from issues where repositoryName = :repositoryName")
    suspend fun deleteRepositoryIssues(repositoryName: String)

    @Query(
        "Select * from issues where repositoryName = :repositoryName",
    )
    fun getRepositoryIssues(
        repositoryName: String,
        // assignee: String?,
        //  labels: String,
    ): PagingSource<Int, CachedIssueEntity>
}
