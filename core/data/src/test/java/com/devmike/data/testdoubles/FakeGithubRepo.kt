package com.devmike.data.testdoubles

import com.devmike.domain.models.AssigneeModel
import com.devmike.domain.models.IssueSearchModel
import com.devmike.domain.models.LabelModel
import com.devmike.network.dto.IssueDTO
import com.devmike.network.dto.PagedDtoWrapper
import com.devmike.network.dto.RepositoryDTO
import com.devmike.network.networkSource.GitHubIssuesRepo
import fakenetworkissuespage1
import fakenetworkissuespage2

class FakeGithubRepo : GitHubIssuesRepo {
    override suspend fun searchRepositories(
        name: String,
        cursor: String?,
        pageSize: Int,
    ): Result<PagedDtoWrapper<List<RepositoryDTO>>> {
        val repositories =
            if (cursor == null) {
                listOf(
                    RepositoryDTO(
                        url = "https://github.com/flutter/flutter",
                        name = "flutter",
                        nameWithOwner = "flutter/flutter",
                        owner = "flutter",
                        description = "Flutter makes it easy and fast to build beautiful apps for mobile and beyond",
                        stargazers = 150000, // Example value
                        forkCount = 25000, // Example value
                        issueCount = 10000, // Example value
                        avatarUrl = "https://example.com/flutter_avatar.png", // Example URL
                    ),
                    RepositoryDTO(
                        url = "https://github.com/JetBrains/kotlin",
                        name = "kotlin",
                        nameWithOwner = "JetBrains/kotlin",
                        owner = "JetBrains",
                        description = "The Kotlin Programming Language.",
                        stargazers = 45000, // Example value
                        forkCount = 6000, // Example value
                        issueCount = 3000, // Example value
                        avatarUrl = "https://example.com/kotlin_avatar.png", // Example URL
                    ),
                    RepositoryDTO(
                        url = "https://github.com/android/android",
                        name = "android",
                        nameWithOwner = "android/android",
                        owner = "android",
                        description = "The Android Open Source Project",
                        stargazers = 80000, // Example value
                        forkCount = 30000, // Example value
                        issueCount = 12000, // Example value
                        avatarUrl = "https://example.com/android_avatar.png", // Example URL
                    ),
                )
            } else {
                emptyList()
            }

        return Result.success(
            PagedDtoWrapper(
                data = repositories,
                hasNextPage = cursor == null,
                nextCursor = if (cursor == null) "cursor" else null,
            ),
        )
    }

    override suspend fun getRepositoryIssues(
        issueSearchModel: IssueSearchModel,
    ): Result<PagedDtoWrapper<List<IssueDTO>>> {
        val issues =
            if (issueSearchModel.cursor == null) {
                fakenetworkissuespage1
            } else {
                fakenetworkissuespage2
            }
        return Result.success(
            PagedDtoWrapper(
                data = issues,
                hasNextPage = issueSearchModel.cursor == null,
                nextCursor = if (issueSearchModel.cursor == null) "cursor" else null,
            ),
        )
    }

    override suspend fun getRepositoryLabels(
        name: String,
        owner: String,
        cursor: String?,
        pageSize: Int,
    ): Result<PagedDtoWrapper<List<LabelModel>>> {
        val labels =
            if (cursor == null) {
                listOf(
                    LabelModel(
                        name = "bug",
                        color = "d73a4a",
                    ),
                    LabelModel(
                        name = "enhancement",
                        color = "a2eeef",
                    ),
                    LabelModel(
                        name = "question",
                        color = "d876e3",
                    ),
                )
            } else {
                emptyList() // No more labels for subsequent pages
            }

        return Result.success(
            PagedDtoWrapper(
                data = labels,
                hasNextPage = cursor == null,
                nextCursor = if (cursor == null) "cursor" else null,
            ),
        )
    }

    override suspend fun getRepositoryAssignees(
        name: String,
        owner: String,
        cursor: String?,
        pageSize: Int,
    ): Result<PagedDtoWrapper<List<AssigneeModel>>> {
        val assignees =
            if (cursor == null) {
                listOf(
                    AssigneeModel(
                        name = "Hixie",
                        username = "hixie",
                        avatarUrl = "https://avatars.githubusercontent.com/u/105637?v=4",
                    ),
                    AssigneeModel(
                        name = "Tim Sneath",
                        username = "timsneath",
                        avatarUrl = "https://avatars.githubusercontent.com/u/65809?v=4",
                    ),
                    AssigneeModel(
                        name = "yjbanov",
                        username = "yjbanov",
                        avatarUrl = "https://avatars.githubusercontent.com/u/44034?v=4",
                    ),
                )
            } else {
                emptyList()
            }

        return Result.success(
            PagedDtoWrapper(
                data = assignees,
                hasNextPage = cursor == null,
                nextCursor = if (cursor == null) "cursor" else null,
            ),
        )
    }
}
