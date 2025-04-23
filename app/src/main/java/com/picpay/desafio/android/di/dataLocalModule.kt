package com.picpay.desafio.android.di

import androidx.room.Room
import com.picpay.desafio.android.data.local.UserLocalDataSource
import com.picpay.desafio.android.data_local.dataSource.UserLocalDataSourceImpl
import com.picpay.desafio.android.data_local.database.AppDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataLocalModule = module {

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "AppDatabase"
        ).build()

    }

    // Tables
    single { get<AppDatabase>().getUserDao() }

    // DataSources
    single<UserLocalDataSource> { UserLocalDataSourceImpl(
        timeProvider = get(),
        userDao = get()
    ) }

}
