package com.devmike.network.utils

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Operation
import com.devmike.domain.helper.AppErrors
import timber.log.Timber
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
            response.exception != null -> {
                Timber.tag("networkerrorexception").d(response.exception)
                when (response.exception!!.cause) {
                    is SocketTimeoutException -> {
                        Result.failure(AppErrors.Timeout(response.exception!!.cause))
                    }

                    is IOException -> {
                        Result.failure(AppErrors.NoInternet(response.exception!!.cause))
                    }

                    else -> {
                        Result.failure(AppErrors.Unknown(cause = response.exception!!.cause))
                    }
                }
            }
            response.hasErrors() -> {
                Timber.tag("networkerrorexception").d("${response.errors}")

                Result.failure(
                    AppErrors.GraphQLError(
                        response.errors?.map { it.message } ?: emptyList(),
                    ),
                )
            }
            else -> {
                Result.failure(
                    AppErrors.Unknown(
                        response.errors?.first()?.message ?: "Something went wrong",
                    ),
                )
            }
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Result.failure(AppErrors.Unknown(cause = e))
    }
