package com.devmike.network.utils

import com.apollographql.apollo.api.http.HttpRequest
import com.apollographql.apollo.api.http.HttpResponse
import com.apollographql.apollo.network.http.HttpInterceptor
import com.apollographql.apollo.network.http.HttpInterceptorChain
import com.devmike.datastore.repo.DataStoreRepo
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * An [HttpInterceptor] implementation that adds an "Authorization" header to outgoing GraphQL requests.
 * If the user's token is available in the [DataStoreRepo], it is used to construct the header.
 * If the response status code indicates an authentication error (401 or 403), the user's token is
 * cleared from the [DataStoreRepo].
 *
 * @param dataStoreRepo The repository used to retrieve and clear the user's token.
 * These response codes indicate that the client is not permitted.
 * [Autherrors](https://github.com/graphql/graphql-over-http/blob/main/spec/GraphQLOverHTTP.md#:~:text=If%20the%20request%20is%20not,or%20similar%20appropriate%20status%20code.)
 */
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
