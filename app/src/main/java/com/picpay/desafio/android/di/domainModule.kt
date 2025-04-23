package com.picpay.desafio.android.di

import com.picpay.desafio.android.domain.useCase.GetUserUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetUserUseCase(userRepository = get())
    }

}
