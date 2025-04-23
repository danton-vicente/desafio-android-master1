package com.picpay.desafio.android.data_remote.utils

import retrofit2.Call

suspend fun <T> executeCall(call: () -> Call<T>): Result<T> {
    return try {
        val response = call().execute()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: Result.Error(ErrorType.HTTP_ERROR, "Response body is null")
        } else {
            Result.Error(ErrorType.HTTP_ERROR, "HTTP error: ${response.code()}", null)
        }
    } catch (e: Exception) {
        when (e) {
            is java.net.UnknownHostException -> Result.Error(ErrorType.NETWORK_ERROR, "Network error", e)
            else -> Result.Error(ErrorType.UNKNOWN_ERROR, "Unknown error", e)
        }
    }
}
