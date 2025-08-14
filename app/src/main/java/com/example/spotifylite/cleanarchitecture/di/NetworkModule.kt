package com.example.spotifylite.cleanarchitecture.di

import com.example.spotifylite.BuildConfig
import com.example.spotifylite.cleanarchitecture.data.remote.JamendoApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.HttpUrl
import okhttp3.Interceptor
import javax.inject.Singleton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
    }

    @Provides @Singleton
    fun provideQueryInterceptor(): Interceptor = Interceptor { chain ->
        val original = chain.request()
        val originalUrl: HttpUrl = original.url
        val newUrl = originalUrl.newBuilder()
            .addQueryParameter("client_id", BuildConfig.JAMENDO_CLIENT_ID)
            .build()
        val newRequest = original.newBuilder().url(newUrl).build()
        chain.proceed(newRequest)
    }

    @Provides
    @Singleton
    fun provideOkHttp(clientIdInterceptor: Interceptor): OkHttpClient {
        val log = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BASIC
        }
        return OkHttpClient.Builder()
            .addInterceptor(clientIdInterceptor)
            .addInterceptor(log)
            .build()
    }

    @Provides @Singleton
    fun provideRetrofit(okHttp: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl("https://api.jamendo.com/v3.0/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .client(okHttp)
            .build()
    }

    @Provides @Singleton
    fun provideJamendoApi(retrofit: Retrofit): JamendoApi =
        retrofit.create(JamendoApi::class.java)

}