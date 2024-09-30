package com.devmike.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.devmike.database.entities.AssigneeEntity
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import com.devmike.database.entities.LabelEntity

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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignees(assignees: List<AssigneeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabels(labels: List<LabelEntity>)

    @Transaction
    @Query(
        """
            SELECT * FROM issues

        WHERE repositoryName = :repoName
        AND (
            (:assigneeSize = 0)
            OR issueId IN (SELECT issueId FROM assignees WHERE assignee IN (:assignees))
        )
        AND (
            (:labelsSize = 0)
            OR issueId IN (SELECT issueId FROM labels WHERE label IN (:labels))
        )
         AND ( :query IS NULL OR issueId IN (SELECT issueId FROM issues_fts WHERE issues_fts MATCH :query))

        AND (:issueState = 'all' OR state = :issueState)

        ORDER BY createdAt DESC



        """,
    )
    fun getIssuesWithAssigneesAndLabels(
        repoName: String,
        assignees: List<String>,
        assigneeSize: Int = assignees.size,
        labels: List<String>?,
        labelsSize: Int = labels?.size ?: 0,
        query: String?,
        issueState: String,
    ): PagingSource<Int, IssueWithAssigneesAndLabels>

    @Transaction
    @Query(
        """
  SELECT * FROM issues

        WHERE repositoryName = :repoName
        AND (
            (:assigneeSize = 0)
            OR issueId IN (SELECT issueId FROM assignees WHERE assignee IN (:assignees))
        )
        AND (
            (:labelsSize = 0)
            OR issueId IN (SELECT issueId FROM labels WHERE label IN (:labels))
        )
        AND ( :query IS NULL OR issueId IN (SELECT issueId FROM issues_fts WHERE issues_fts MATCH :query))


        AND (:issueState = 'all' OR state = :issueState)

        ORDER BY createdAt DESC



        """,
    )
    suspend fun getIssuesWithAssignees(
        repoName: String,
        assignees: List<String>,
        assigneeSize: Int = assignees.size,
        labels: List<String>?,
        labelsSize: Int = labels?.size ?: 0,
        query: String,
        issueState: String,
    ): List<IssueWithAssigneesAndLabels>

    @Query(
        """
        DELETE FROM assignees WHERE issueId IN (SELECT id FROM issues WHERE repositoryName = :repositoryName)
        """,
    )
    suspend fun deleteAssigneesForIssue(repositoryName: String)

    @Query(
        """
        DELETE FROM labels
        WHERE issueId IN (SELECT id FROM issues WHERE repositoryName = :repositoryName)
        """,
    )
    suspend fun deleteLabelsForIssue(repositoryName: String)
}
