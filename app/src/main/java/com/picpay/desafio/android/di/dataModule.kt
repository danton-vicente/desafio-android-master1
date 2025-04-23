package com.picpay.desafio.android.di

import com.picpay.desafio.android.commons.utils.NetworkChecker
import com.picpay.desafio.android.commons.utils.NetworkCheckerImpl
import com.picpay.desafio.android.commons.utils.TimeProvider
import com.picpay.desafio.android.commons.utils.TimeProviderImpl
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<NetworkChecker> { NetworkCheckerImpl(androidContext()) }
    single<TimeProvider> { TimeProviderImpl() }

    single<UserRepository> {
        UserRepositoryImpl(
            timeProvider = get(),
            networkChecker = get(),
            userRemoteDataSource = get(),
            userLocalDataSource = get()
        )
    }
}
