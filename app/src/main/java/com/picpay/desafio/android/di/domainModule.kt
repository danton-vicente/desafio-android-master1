package com.picpay.desafio.android.di

import com.picpay.desafio.android.domain.useCase.GetCommentsUseCase
import com.picpay.desafio.android.domain.useCase.GetCompleteUserInfoUseCase
import com.picpay.desafio.android.domain.useCase.GetUserUseCase
import com.picpay.desafio.android.domain.useCase.SetCommentsUseCase
import org.koin.dsl.module

val domainModule = module {

    factory {
        GetUserUseCase(userRepository = get())
    }

    factory {
        GetCompleteUserInfoUseCase(userRepository = get())
    }

    factory {
        GetCommentsUseCase(
            userRepository = get()
        )
    }

    factory {
        SetCommentsUseCase(
            userRepository = get()
        )
    }

}
