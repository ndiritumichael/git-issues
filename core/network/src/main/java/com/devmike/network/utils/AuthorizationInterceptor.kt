package com.devmike.network.utils

import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import com.devmike.datastore.repo.DataStoreRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthorizationInterceptor
    @Inject
    constructor(
        private val dataStoreRepo: DataStoreRepo,
    ) : HttpInterceptor {
        override suspend fun intercept(
            request: HttpRequest,
            chain: HttpInterceptorChain,
        ): HttpResponse {
            val token = dataStoreRepo.getUserToken().first()
            val newRequest =
                request
                    .newBuilder()
                    .apply {
                        token?.let {
                            this.addHeader("Authorization", "Bearer $token")
                        }
                    }.build()

            val response = chain.proceed(newRequest)

            if (response.statusCode in Autherrors) {
                dataStoreRepo.clearUserToken()
            }

            return response
        }
    }

val Autherrors = listOf(401, 403)
