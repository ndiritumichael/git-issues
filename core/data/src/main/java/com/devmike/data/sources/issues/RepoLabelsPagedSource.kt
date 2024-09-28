package com.devmike.data.sources.issues

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingState
import com.devmike.domain.models.LabelModel
import com.devmike.network.networkSource.GitHubIssuesRepo

class RepoLabelsPagedSource(
    private val gitHubIssuesRepo: GitHubIssuesRepo,
    private val name: String,
    private val owner: String,
) : PagingSource<String, LabelModel>() {
    override fun getRefreshKey(state: PagingState<String, LabelModel>): String? = null

    override suspend fun load(params: LoadParams<String>): LoadResult<String, LabelModel> {
        val cursor = params.key

        return try {
            val response =
                gitHubIssuesRepo.getRepositoryLabels(
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
