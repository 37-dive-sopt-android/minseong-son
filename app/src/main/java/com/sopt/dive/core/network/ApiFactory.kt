package com.sopt.dive.core.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import com.sopt.dive.BuildConfig

object ApiFactory {

    private const val BASE_URL_REQUIRED = BuildConfig.BASE_URL_REQUIRED
    private const val BASE_URL_ADVANCED = BuildConfig.BASE_URL_ADVANCED

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    val retrofitRequired: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_REQUIRED)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val retrofitAdvanced: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_ADVANCED)
            .client(client)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> createRequired(): T = retrofitRequired.create(T::class.java)

    inline fun <reified T> createAdvanced(): T = retrofitAdvanced.create(T::class.java)
}
