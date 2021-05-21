package com.example.notecalendar.di

import com.example.notecalendar.freamwork.datasource.network.implementation.BaseApi
import com.example.notecalendar.freamwork.datasource.network.implementation.HeadersInterceptor
import com.example.notecalendar.freamwork.datasource.network.implementation.NoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpBuilder() = OkHttpClient.Builder().addInterceptor(HeadersInterceptor())

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder().baseUrl(BaseApi.URL).client(okHttpClient).build()

    @Provides
    fun proveNoteService(retrofit: Retrofit) = retrofit.create(NoteService::class.java)
}