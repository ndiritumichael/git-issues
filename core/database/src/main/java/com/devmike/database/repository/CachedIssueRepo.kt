package com.devmike.database.repository

import androidx.paging.PagingSource
import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.CachedIssueEntity
import com.devmike.database.entities.CachedIssueKeyEntity

/**
 *The purpose of this interface is to abstract the daatabase and its
 *  transactions from th data layer which assists
 *  in testing the data layer without having to provide the  db implentations
 */

interface CachedIssueRepo {
    suspend fun insertIssues(
        isRefresh: Boolean,
        nextCursor: String?,
        issueDTO: CachedIssueDTO,
        issues: List<CachedIssueEntity>,
    )

    fun getIssueByRepository(
        repository: String,
        assignee: String?,
        labels: List<String>,
    ): PagingSource<Int, CachedIssueEntity>

    suspend fun remoteKeyByQuery(
        repository: String,
        assignee: String?,
        labels: List<String>,
    ): CachedIssueKeyEntity?
}
