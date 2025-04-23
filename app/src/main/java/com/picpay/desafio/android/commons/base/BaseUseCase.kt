package com.picpay.desafio.android.commons.base

abstract class BaseUseCase<in Params, out T> {

    abstract suspend fun execute(params: Params): T

    suspend operator fun invoke(
        params: Params,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        try {
            val result = execute(params)
            onSuccess(result)
        } catch (exception: Throwable) {
            onError(exception)
        }
    }
}
