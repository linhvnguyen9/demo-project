package com.linh.core.data.remote.utils

import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException

suspend inline fun <reified T, reified E: Throwable> safeApiCall(
    apiCall: () -> T,
): Result<T> {
    try {
        val response = apiCall.invoke()
        return Result.success(response)
    } catch (e: ClientRequestException) {
        return Result.failure(handleErrorResponse<E>(e.response))
    } catch (e: RedirectResponseException) {
        return Result.failure(handleErrorResponse<E>(e.response))
    } catch (e: ServerResponseException) {
        return Result.failure(handleErrorResponse<E>(e.response))
    } catch (e: UnresolvedAddressException) {
        return Result.failure(NetworkException.ConnectionError)
    } catch (e: Exception) {
        println(e)
        return Result.failure(NetworkException.UnknownError)
    }
}

sealed class NetworkException : Exception() {
    data object ConnectionError: NetworkException()
    data object UnknownError: NetworkException()
}

suspend inline fun <reified E: Throwable> handleErrorResponse(
    response: HttpResponse
): Throwable {
    return try {
        response.body<E>()
    } catch (e: SerializationException) {
        Error("Unknown error")
    }
}