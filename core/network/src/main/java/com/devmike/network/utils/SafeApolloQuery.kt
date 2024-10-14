package com.devmike.network.utils

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Operation
import com.devmike.domain.helper.AppErrors
import timber.log.Timber
import java.io.IOException
import java.net.SocketTimeoutException
import kotlin.coroutines.cancellation.CancellationException

/**
 * Executes an Apollo GraphQL query safely and handles potential errors.
 *
 * This function wraps the execution of an Apollo GraphQL query within a try-catch block
 * and handles various error scenarios, returning a [Result] object that encapsulates
 * either the successful query data or a specific error.
 *
 * @param T The type of the query data.
 * @param query A lambda function that returns an [ApolloCall] representing the GraphQL query.
 * @return A [Result] object containing either the successful query data of type [T]
 *         or a specific error derived from [AppErrors].
 *
 */
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
