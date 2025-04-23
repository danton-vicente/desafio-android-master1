package com.picpay.desafio.android.di

import com.picpay.desafio.android.presentation.ContactDetailsScreenViewModel
import com.picpay.desafio.android.presentation.viewmodels.ContactsScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    viewModel {
        ContactsScreenViewModel(
            getUserUseCase = get(),
        )
    }

    viewModel {
        ContactDetailsScreenViewModel(
            getCompleteUserInfoUseCase = get(),
            getCommentsUseCase = get(),
            setCommentsUseCase = get(),
        )
    }
}
