package com.fatec.ktor.network

import com.fatec.ktor.data.model.ApiError

sealed class ApiResult<out T> {

    data class Success<out T>(val data: T) : ApiResult<T>()

    sealed class Error(open val originalException: Throwable?) : ApiResult<Nothing>() {
        data class HttpError(
            val code: Int,
            val errorBody: ApiError?,
            override val originalException: Throwable?
        ) : Error(originalException)

        data class NetworkError(override val originalException: Throwable) : Error(originalException)
        data class UnknownError(override val originalException: Throwable) : Error(originalException)
    }
}
