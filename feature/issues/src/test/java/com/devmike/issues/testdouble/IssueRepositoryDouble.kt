package com.devmike.issues.testdouble

import androidx.compose.ui.test.filter
import androidx.paging.PagingData
import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import com.devmike.domain.repository.IssuesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class IssueRepositoryDouble : IssuesRepository {
    override fun getRepositoryLabels(
        owner: String,
        repository: String,
    ): Flow<PagingData<LabelModel>> =
        if (owner == "flutter" && repository == "flutter") {
            flowOf(PagingData.from(fakeFlutterLabels))
        } else {
            flowOf(PagingData.empty())
        }

    override fun getRepositoryAssignees(
        owner: String,
        repository: String,
    ): Flow<PagingData<AssigneeModel>> =
        if (owner == "flutter" && repository == "flutter") {
            flowOf(PagingData.from(fakeFlutterAssignees))
        } else {
            flowOf(PagingData.empty())
        }

    override fun getPagedIssues(issueSearchModel: IssueSearchModel): Flow<PagingData<IssueModel>> {
        val filteredIssues =
            when (issueSearchModel.repository) {
                "flutter/flutter" ->
                    fakeFlutterIssues.filter {
                        when (issueSearchModel.issueState) {
                            "open" -> it.state == "open"
                            "closed" -> it.state == "closed"
                            else -> true
                        }
                    }
                "ktorio/ktor" ->
                    fakektorIssues.filter {
                        when (issueSearchModel.issueState) {
                            "open" -> it.state == "open"
                            "closed" -> it.state == "closed"
                            else -> true
                        }
                    }
                else -> emptyList()
            }

        return flowOf(PagingData.from(filteredIssues))
    }
}
