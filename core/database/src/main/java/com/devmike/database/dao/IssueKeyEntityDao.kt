package com.devmike.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.devmike.database.entities.CachedIssueKeyEntity

/**
 * A DAO or interacting with the [CachedIssueKeyEntity] in the database.
 * Provides methods for querying and manipulating the cursor key for the issues query in the table.
 */
@Dao
interface IssueKeyEntityDao {
    @Query(
        """ select * FROM cachedissuekeyentity
 WHERE repoName = :repoName
 AND assignee = :assignee AND
  labels = :labels
   AND `query` = :query
   AND issueState = :issueState


            """,
    )
    suspend fun getIssuesKey(
        repoName: String,
        assignee: List<String>,
        labels: List<String>,
        query: String?,
        issueState: String,
        // sortBy: String?,
    ): CachedIssueKeyEntity?

    @Upsert
    suspend fun insertRemoteKey(remoteKey: CachedIssueKeyEntity)

    @Query(
        """ DELETE FROM cachedissuekeyentity
 WHERE repoName = :repoName
 AND assignee = :assignee AND
  labels = :labels
   AND `query` = :query
   AND issueState = :issueState

   """,
    )
    suspend fun clearRemoteKeysForQuery(
        repoName: String,
        assignee: List<String>?,
        labels: List<String>?,
        query: String?,
        issueState: String,
    )
}
