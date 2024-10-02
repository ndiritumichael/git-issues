package com.devmike.data.sources.issues

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingState
import com.devmike.domain.models.AssigneeModel
import com.devmike.network.networkSource.GitHubIssuesRepo

class RepoAssigneesPagedSource(
    private val gitHubIssuesRepo: GitHubIssuesRepo,
    private val name: String,
    private val owner: String,
) : PagingSource<String, AssigneeModel>() {
    override fun getRefreshKey(state: PagingState<String, AssigneeModel>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, AssigneeModel> {
        val cursor = params.key

        return try {
            val response =
                gitHubIssuesRepo.getRepositoryAssignees(
                    name,
                    owner,
                    cursor,
                    params.loadSize,
                )
            response.fold(
                onFailure = {
                    Error(it)
                },
                onSuccess = { pagedDtoWrapper ->

                    LoadResult.Page(
                        data = pagedDtoWrapper.data,
                        prevKey = null,
                        nextKey = pagedDtoWrapper.nextCursor,
                    )
                },
            )
        } catch (e: Exception) {
            Error(e)
        }
    }
}
