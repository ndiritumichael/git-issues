package com.devmike.network.dto

data class PagedDtoWrapper<T>(
    val nextCursor: String?,
    val hasNextPage: Boolean,
    val data: T,
)
