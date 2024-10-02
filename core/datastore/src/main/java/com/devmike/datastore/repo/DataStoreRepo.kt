package com.devmike.datastore.repo

import kotlinx.coroutines.flow.Flow

interface DataStoreRepo {
    suspend fun insertToken(token: String)

    fun getUserToken(): Flow<String?>

    suspend fun clearUserToken()
}
