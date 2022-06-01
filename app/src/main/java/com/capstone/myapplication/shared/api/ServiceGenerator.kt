package com.capstone.myapplication.shared.api

import com.capstone.myapplication.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {
    private const val BASE_URL: String = "https://story-api.dicoding.dev/v1/"

    private val logging =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.BASIC
        )

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
    var retrofit = builder.build()
    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        .addInterceptor(logging)

    fun setToken(token: String?) {
        val interceptor = JWTInterceptor(token)
        httpClient.addInterceptor(interceptor)
    }

    fun <S> createService(serviceClass: Class<S>): S {
        builder.client(httpClient.build())
        retrofit = builder.build()
        return retrofit.create(serviceClass)
    }
}