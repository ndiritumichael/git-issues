package com.devmike.network.networkSource

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
        name: String,
        owner: String,
        cursor: String? = null,
        pageSize: Int = 10,
    ): Result<PagedDtoWrapper<List<IssueDTO>>>
}
