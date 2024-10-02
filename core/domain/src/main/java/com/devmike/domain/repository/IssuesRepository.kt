package com.devmike.domain.repository

import androidx.paging.PagingData
import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import kotlinx.coroutines.flow.Flow

interface IssuesRepository {
    fun getRepositoryLabels(
        owner: String,
        repository: String,
    ): Flow<PagingData<LabelModel>>

    fun getRepositoryAssignees(
        owner: String,
        repository: String,
    ): Flow<PagingData<AssigneeModel>>

    fun getPagedIssues(issueSearchModel: IssueSearchModel): Flow<PagingData<IssueModel>>
}
