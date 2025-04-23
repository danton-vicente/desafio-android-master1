package com.picpay.desafio.android.data_remote.utils


import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit


object WebServiceFactory {

    inline fun <reified T> createWebService(
        wasDebugVersion: Boolean,
        okHttpClient: OkHttpClient,
        needBaseUrl: Boolean = true,
        url: String,
    ): T {

        val gsonFactory = GsonConverterFactory.create(
            GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create()
        )

        val baseUrl = when (needBaseUrl) {
            true -> getBaseUrl(wasDebugVersion).plus(url)
            false -> url
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(UnitConverterFactory)
            .addConverterFactory(gsonFactory)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
        return retrofit.create()
    }

    fun getBaseUrl(wasDebugVersion: Boolean) =
        if (wasDebugVersion) "https://68084544942707d722ddb86c.mockapi.io/"
        else "https://68084544942707d722ddb86c.mockapi.io/"


    fun provideOkHttpClient(
        wasDebugVersion: Boolean,
    ): OkHttpClient = OkHttpClient.Builder()
        .dispatcher(dispatcher())
        .httpLoggingInterceptor(wasDebugVersion)
        .connectTimeout(30L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .writeTimeout(30L, TimeUnit.SECONDS)
        .build()

    private fun OkHttpClient.Builder.httpLoggingInterceptor(wasDebugVersion: Boolean) =
        when (wasDebugVersion) {
            true -> {
                val interceptor = HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
                addInterceptor(interceptor)
            }

            else -> this
        }


    private fun dispatcher() = Dispatcher().run {
        maxRequests = 1
        maxRequestsPerHost = 1
        this
    }

    object UnitConverterFactory : Converter.Factory() {
        override fun responseBodyConverter(
            type: Type, annotations: Array<out Annotation>,
            retrofit: Retrofit
        ): Converter<ResponseBody, *>? {
            return if (type == Unit::class.java) UnitConverter else null
        }

        private object UnitConverter : Converter<ResponseBody, Unit> {
            override fun convert(value: ResponseBody) {
                value.close()
            }
        }
    }

}
