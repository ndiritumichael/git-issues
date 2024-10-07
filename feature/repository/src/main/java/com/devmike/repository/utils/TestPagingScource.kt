package com.devmike.repository.utils

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

fun <T : Any> createTestPagingFlow(
    items: List<T>,
    refreshState: LoadState = LoadState.NotLoading(endOfPaginationReached = false),
    appendState: LoadState = LoadState.NotLoading(endOfPaginationReached = false),
    prependState: LoadState = LoadState.NotLoading(endOfPaginationReached = false)
): Flow<PagingData<T>> {
    return flowOf(
        PagingData.from(
            data = items,
            sourceLoadStates = LoadStates(
                refresh = refreshState,
                append = appendState,
                prepend = prependState
            )
        )
    )
}
