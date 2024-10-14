package com.devmike.network.utils

import com.apollographql.apollo.api.ApolloRequest
import com.apollographql.apollo.api.ApolloResponse
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

/**
 * A custom Apollo interceptor that logs the received response for each request.
 *
 * @param request The Apollo request to be sent.
 * @param chain The Apollo interceptor chain to be used for further processing.
 *
 * @return A Flow of Apollo responses, where each response is logged before being returned.
 */
class LoggingApolloInterceptor : ApolloInterceptor {
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain,
    ): Flow<ApolloResponse<D>> =
        chain.proceed(request).onEach { response ->
            println("Received response for ${request.operation.name()}: ${response.data}")
        }
}
