package com.devmike.database.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "issues")
data class CachedIssueEntity(
    @PrimaryKey
    val id: String,
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
        parentColumn = "id",
        entityColumn = "issueId",
    )
    val assignees: List<AssigneeEntity>,
    @Relation(
        parentColumn = "id",
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
)
