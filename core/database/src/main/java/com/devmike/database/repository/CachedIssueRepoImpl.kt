package com.devmike.database.repository

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.devmike.database.GithubDatabase
import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.CachedIssueKeyEntity
import com.devmike.database.entities.IssueWithAssigneesAndLabels
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Inject

class CachedIssueRepoImpl
    @Inject
    constructor(
        private val db: GithubDatabase,
    ) : CachedIssueRepo {
        override suspend fun insertIssues(
            isRefresh: Boolean,
            nextCursor: String?,
            issueDTO: CachedIssueDTO,
            issues: List<IssueWithAssigneesAndLabels>,
        ) {
            db.withTransaction {
                if (isRefresh) {
                    db.issueKeyDao().clearRemoteKeysForQuery(
                        repoName = issueDTO.repository,
                        assignee = issueDTO.assignee,
                        labels = issueDTO.labels,
                        query = issueDTO.query,
                        issueState = issueDTO.issueStatus,
                    )

                    db.issueDao().deleteRepositoryIssues(
                        issueDTO.repository,
                    )

                    db.issueDao().deleteLabelsForIssue(issueDTO.repository)
                    db.issueDao().deleteLabelsForIssue(issueDTO.repository)
                }

                db.issueDao().insertIssues(issues.map { it.issue })

                db.issueDao().insertAssignees(
                    issues.flatMap { it.assignees },
                )

                db.issueDao().insertLabels(
                    issues.flatMap { it.labels },
                )

                db.issueKeyDao().insertRemoteKey(
                    CachedIssueKeyEntity(
                        nextCursor = nextCursor,
                        repoName = issueDTO.repository,
                        assignee = issueDTO.assignee,
                        labels =
                            issueDTO.labels,
                        issueState = issueDTO.issueStatus,
                        query = issueDTO.query,
                        sortBy = issueDTO.sortBy,
                    ),
                )
            }
        }

        @OptIn(DelicateCoroutinesApi::class)
        override fun getIssueByRepository(
            repository: String,
            assignee: List<String>?,
            labels: List<String>?,
            queryString: String,
            issueState: String,
        ): PagingSource<Int, IssueWithAssigneesAndLabels> =
            db.issueDao().getIssuesWithAssigneesAndLabels(
                repoName = repository,
                assignees = assignee ?: emptyList(),
                labels = labels,
                query = queryString,
                issueState = issueState,
            )

        override suspend fun remoteKeyByQuery(issueDTO: CachedIssueDTO): CachedIssueKeyEntity? =
            db.issueKeyDao().getIssuesKey(
                repoName = issueDTO.repository,
                assignee = issueDTO.assignee,
                labels = issueDTO.labels,
                query = issueDTO.query,
                issueState = issueDTO.issueStatus,
                // sortBy = issueDTO.sortBy,
            )
    }
