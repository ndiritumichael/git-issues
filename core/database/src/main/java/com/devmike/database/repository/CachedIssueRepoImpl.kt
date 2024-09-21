package com.devmike.database.repository

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.devmike.database.GithubDatabase
import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.CachedIssueKeyEntity
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
            issues: List<CachedIssueEntity>,
        ) {
            db.withTransaction {
                if (isRefresh) {
                    db.issueKeyDao().clearRemoteKeysForQuery(
                        issueDTO.repository,
                        issueDTO.assignee,
                        issueDTO.labels.sorted().joinToString(),
                    )

                    db.issueDao().deleteRepositoryIssues(
                        issueDTO.repository,
                    )
                }

                db.issueDao().insertIssues(issues)
                db.issueKeyDao().insertRemoteKey(
                    CachedIssueKeyEntity(
                        nextCursor = nextCursor,
                        repoName = issueDTO.repository,
                        assignee = issueDTO.assignee,
                        labels = issueDTO.labels.sorted().joinToString(),
                    ),
                )
            }
        }

        override fun getIssueByRepository(
            repository: String,
            assignee: String?,
            labels: List<String>,
        ): PagingSource<Int, CachedIssueEntity> =
            db.issueDao().getRepositoryIssues(
                repository,
                // assignee, labels.sorted().joinToString())
            )

        override suspend fun remoteKeyByQuery(
            repository: String,
            assignee: String?,
            labels: List<String>,
        ): CachedIssueKeyEntity? = db.issueKeyDao().getIssuesKey(repository)
    }
