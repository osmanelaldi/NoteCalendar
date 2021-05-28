package com.example.notecalendar.di

import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.freamwork.datasource.network.implementation.BaseApi
import com.example.notecalendar.freamwork.datasource.network.implementation.HeadersInterceptor
import com.example.notecalendar.freamwork.datasource.network.implementation.NoteNetworkServiceImpl
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
    fun provideNoteService(retrofit: Retrofit) = retrofit.create(NoteService::class.java)

    @Provides
    fun noteNetworkDataSource(noteService: NoteService) : NoteNetworkDataSource{
        return NoteNetworkServiceImpl(noteService)
    }

}