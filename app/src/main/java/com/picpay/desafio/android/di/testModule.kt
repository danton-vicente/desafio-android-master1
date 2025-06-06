package com.picpay.desafio.android.di

import com.picpay.desafio.android.commons.utils.TestContextProvider
import com.picpay.desafio.android.commons.utils.ThreadContextProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.koin.dsl.module

fun <R> Flow<R>.testFlow(test: R.() -> Unit) {
    runBlocking {
        collect {
            it.test()
        }
    }
}

val testModule = module {
    single<ThreadContextProvider> { TestContextProvider() }
}
