package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.dataLocalModule
import com.picpay.desafio.android.di.dataModule
import com.picpay.desafio.android.di.dataRemoteModule
import com.picpay.desafio.android.di.domainModule
import com.picpay.desafio.android.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin()
    }

    private fun startKoin() {
        startKoin {
            androidLogger()
            modules(
                listOf(
                    domainModule,
                    dataRemoteModule,
                    dataModule,
                    dataLocalModule,
                    presentationModule,
                )
            ).androidContext(applicationContext)
        }
    }
}
