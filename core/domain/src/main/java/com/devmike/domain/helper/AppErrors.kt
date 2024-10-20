package com.devmike.domain.helper

sealed class AppErrors(
    override val message: String,
    override val cause: Throwable? = null,
) : Exception(message, cause) {
    class NoInternet(
        cause: Throwable? = null,
    ) : AppErrors(
            "No internet connection, " +
                "Please Ensure you have an internet connection",
            cause,
        )

    class Timeout(
        cause: Throwable? = null,
    ) : AppErrors("Connection Timed Out, Please Try Again")

    class Unknown(
        override val message: String = "An Unknown Error Occurred",
        override val cause: Throwable? = null,
    ) : AppErrors(message, cause)

    class NotFound : AppErrors("Could not find any Assets")

    class Empty : AppErrors("We could not find any items please search another item")

    class Unauthorized : AppErrors("You are not authorized to make this request")

    class GraphQLError(
        val error: List<String>,
    ) : AppErrors("Something went wrong")
}
