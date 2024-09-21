package com.devmike.data.mapper

import com.devmike.database.entities.CachedIssueEntity
import com.devmike.domain.models.IssueModel
import com.devmike.network.dto.IssueDTO

fun IssueDTO.toEntity(): CachedIssueEntity =
    CachedIssueEntity(
        id = id,
        title = title,
        state = state,
        author = author,
        createdAt = createdAt,
        url = url,
        label = label.sorted().toString(),
        repositoryName = repositoryName,
        assignee = assignees.sorted().joinToString(),
        bodyText = bodyText,
    )

fun CachedIssueEntity.toIssueModel(): IssueModel =
    IssueModel(
        state = state,
        url = url,
        title = title,
        createdAt = createdAt,
        label = label.split(","),
        assignee = assignee?.split(",") ?: emptyList(),
        author = author,
        bodyText = bodyText,
        id = id,
        repositoryName = repositoryName,
    )
