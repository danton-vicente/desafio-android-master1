package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.remote.UserRemoteDataSource
import com.picpay.desafio.android.data_remote.dataSource.UserRemoteDataSourceImpl
import com.picpay.desafio.android.data_remote.service.UserWebService
import com.picpay.desafio.android.data_remote.utils.WebServiceFactory
import org.koin.dsl.module

val dataRemoteModule = module {

    single {
        WebServiceFactory.provideOkHttpClient(
            wasDebugVersion = true // hardcoded but need come from buildConfig
        )
    }

    single {
        WebServiceFactory.createWebService(
            wasDebugVersion = true, // hardcoded but need come from buildConfig
            okHttpClient = get(),
            url = "users/desafio/picpay/"
        ) as UserWebService
    }

    single<UserRemoteDataSource> {
        UserRemoteDataSourceImpl(
            userWebService = get()
        )
    }

}
