package com.devmike.network.networkSource

import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import com.devmike.network.dto.IssueDTO
import com.devmike.network.dto.PagedDtoWrapper
import com.devmike.network.dto.RepositoryDTO

interface GitHubIssuesRepo {
    suspend fun searchRepositories(
        name: String,
        cursor: String? = null,
        pageSize: Int = 10,
    ): Result<PagedDtoWrapper<List<RepositoryDTO>>>

    suspend fun getRepositoryIssues(
        issueSearchModel: IssueSearchModel,
    ): Result<PagedDtoWrapper<List<IssueDTO>>>

    suspend fun getRepositoryLabels(
        name: String,
        owner: String,
        cursor: String? = null,
        pageSize: Int = 10,
    ): Result<PagedDtoWrapper<List<LabelModel>>>

    suspend fun getRepositoryAssignees(
        name: String,
        owner: String,
        cursor: String? = null,
        pageSize: Int = 10,
    ): Result<PagedDtoWrapper<List<AssigneeModel>>>
}
