package com.devmike.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "issues")
data class CachedIssueEntity(
    @PrimaryKey
    val issueId: String,
    val bodyText: String? = null,
    val state: String,
    val url: String,
    val title: String,
    val createdAt: String,
    val author: String,
    val repositoryName: String,
)

data class IssueWithAssigneesAndLabels(
    @Embedded val issue: CachedIssueEntity,
    @Relation(
        parentColumn = "issueId",
        entityColumn = "issueId",
    )
    val assignees: List<AssigneeEntity>,
    @Relation(
        parentColumn = "issueId",
        entityColumn = "issueId",
    )
    val labels: List<LabelEntity>,
)

@Entity(tableName = "assignees")
data class AssigneeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val issueId: String,
    val assignee: String,
)

@Entity(tableName = "labels", indices = [Index(value = ["issueId", "label"], unique = true)])
data class LabelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val issueId: String,
    val label: String,
    val color: String,
)

@Fts4(contentEntity = CachedIssueEntity::class)
@Entity(tableName = "issues_fts")
data class CachedIssueFts(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val rowId: Int = 0,
    val issueId: String,
    val title: String?,
    val bodyText: String?,
)
