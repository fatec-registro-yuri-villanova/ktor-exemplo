package com.fatec.ktor.network

import com.fatec.ktor.data.model.ApiError
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.statement.HttpResponse
import java.io.IOException

object ApiCallHandler {
    suspend inline fun <reified T> safeApiCall(
        crossinline apiCall: suspend () -> HttpResponse
    ): ApiResult<T> {
        return try {
            val response = apiCall()
            if (response.status.value in 200..299) {
                ApiResult.Success(response.body())
            } else {
                val errorBody = try {
                    response.body<ApiError>()
                } catch (e: Exception) {
                    null
                }
                ApiResult.Error.HttpError(response.status.value, errorBody, null) // Original exception can be added if needed
            }
        } catch (e: ClientRequestException) {
            // Ktor specific exception for 4xx & 5xx responses
            val errorBody = try {
                e.response.body<ApiError>()
            } catch (decodeEx: Exception) {
                null
            }
            ApiResult.Error.HttpError(e.response.status.value, errorBody, e)
        } catch (e: IOException) {
            // For network issues like no internet connection
            ApiResult.Error.NetworkError(e)
        } catch (e: Exception) {
            // For any other unexpected errors
            ApiResult.Error.UnknownError(e)
        }
    }
}