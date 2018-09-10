package com.iterika.walli.net

import com.iterika.walli.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class Api @Inject constructor() {

    private val BASE_URL = "http://www.walli.richcode.ru/api/run/"

    lateinit var service : IApi

    init {
        createRetrofit()
    }

    private fun createRetrofit() {
        val clientBuilder = OkHttpClient.Builder()

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        if (BuildConfig.DEBUG) {
            clientBuilder.addInterceptor(interceptor)
        }

        val retrofit = retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(clientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        service = retrofit.create(IApi::class.java)
    }
}