package com.devmike.network.utils

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloNetworkException
import com.devmike.domain.helper.AppErrors
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun <reified T : Operation.Data> safeApolloQuery(
    query: () -> ApolloCall<T>,
): Result<T> =
    try {
        val response = query().execute()

        when {
            response.data != null -> {
                Result.success(response.data!!)
            }
            response.hasErrors() -> {
                val firstError = response.errors?.firstOrNull()
                when {
                    firstError?.message?.contains("NOT_FOUND", ignoreCase = true) == true -> {
                        Result.failure(AppErrors.NotFound())
                    }
                    else -> {
                        Result.failure(
                            AppErrors.GraphQLError(
                                response.errors?.first()?.message ?: "Something went wrong",
                            ),
                        )
                    }
                }
            }
            else -> {
                Result.failure(AppErrors.Unknown())
            }
        }
    } catch (e: ApolloNetworkException) {
        when (e.cause) {
            is SocketTimeoutException -> Result.failure(AppErrors.Timeout())
            is IOException -> Result.failure(AppErrors.NoInternet())
            else -> Result.failure(AppErrors.Unknown(cause = e))
        }
    } catch (e: ApolloException) {
        throw e
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(AppErrors.Unknown(cause = e))
    }
