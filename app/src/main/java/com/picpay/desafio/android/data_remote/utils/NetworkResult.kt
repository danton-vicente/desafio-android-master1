package com.picpay.desafio.android.data_remote.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val errorType: ErrorType,
        val message: String? = null,
        val cause: Throwable? = null) : Result<Nothing>()
}

enum class ErrorType {
    NETWORK_ERROR,
    HTTP_ERROR,
    UNKNOWN_ERROR
}
