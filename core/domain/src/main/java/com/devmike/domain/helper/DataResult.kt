package com.devmike.domain.helper

sealed interface DataResult<out T> {
    data class Success<T>(
        val successData: T,
    ) : DataResult<T>

    data class Error(
        val message: String,
        val exception: Exception,
    ) : DataResult<Nothing>
}
