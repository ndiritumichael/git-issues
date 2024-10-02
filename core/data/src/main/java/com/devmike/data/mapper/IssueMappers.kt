package com.devmike.data.mapper

import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.AssigneeEntity
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import com.devmike.database.entities.LabelEntity
import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import com.devmike.network.dto.IssueDTO
import java.util.Locale

fun IssueDTO.toEntity(): CachedIssueEntity =
    CachedIssueEntity(
        issueId = id,
        title = title,
        state = state.lowercase(Locale.ROOT),
        author = author,
        createdAt = createdAt,
        url = url,
        repositoryName = repositoryName,
        bodyText = bodyText,
    )

fun CachedIssueEntity.toIssueModel(
    labels: List<LabelModel>,
    assignee: List<String>,
): IssueModel =
    IssueModel(
        state = state.lowercase(Locale.ROOT),
        url = url,
        title = title,
        createdAt = createdAt,
        label = labels,
        assignee = assignee,
        author = author,
        bodyText = bodyText,
        id = issueId,
        repositoryName = repositoryName,
    )

fun IssueSearchModel.toCachedIssueDTO(): CachedIssueDTO =
    CachedIssueDTO(
        assignee = assignees ?: emptyList(),
        labels = labels ?: emptyList(),
        repository = repository,
        sortBy = sortBy,
        query = query,
        issueStatus = issueState,
    )

fun IssueDTO.toIssuesWithLabelsandAssignees(): IssueWithAssigneesAndLabels {
    val labelsEntity =
        labels.map {
            LabelEntity(
                issueId = id,
                label = it.name,
                color = it.color,
            )
        }

    val assigneesEntity =
        assignees.map {
            AssigneeEntity(
                issueId = id,
                assignee = it,
            )
        }
    return IssueWithAssigneesAndLabels(
        issue = toEntity(),
        labels = labelsEntity,
        assignees = assigneesEntity,
    )
}

fun LabelEntity.toModel(): LabelModel = LabelModel(name = label, color = color)
