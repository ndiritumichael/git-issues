package com.devmike.database.repository

import androidx.paging.PagingSource
import com.devmike.database.dao.models.CachedIssueDTO
import com.devmike.database.entities.CachedIssueKeyEntity
import com.devmike.database.entities.IssueWithAssigneesAndLabels

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
        issues: List<IssueWithAssigneesAndLabels>,
    )

    fun getIssueByRepository(
        repository: String,
        assignee: List<String>?,
        labels: List<String>?,
        queryString: String?,
        issueState: String,
    ): PagingSource<Int, IssueWithAssigneesAndLabels>

    suspend fun remoteKeyByQuery(issueDTO: CachedIssueDTO): CachedIssueKeyEntity?
}
