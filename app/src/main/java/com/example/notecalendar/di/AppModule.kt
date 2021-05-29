package com.example.notecalendar.di

import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.data.network.implementation.NoteNetworkDataSourceImpl
import com.example.notecalendar.business.interactors.GetNotes
import com.example.notecalendar.business.interactors.UpsertNote
import com.example.notecalendar.freamwork.datasource.network.abstraction.NoteNetworkService
import com.example.notecalendar.freamwork.datasource.network.implementation.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideOkHttpBuilder() = OkHttpClient.Builder().addInterceptor(HeadersInterceptor()).build()

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) =
        Retrofit.Builder().baseUrl(BaseApi.URL).
            addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).
            addConverterFactory(NullOnEmptyConverterFactory()).
            client(okHttpClient).
            build()

    @Provides
    fun provideNoteService(retrofit: Retrofit) = retrofit.create(NoteService::class.java)

    @Provides
    fun provideNoteNetworkService(noteService: NoteService) : NoteNetworkService{
        return NoteNetworkServiceImpl(noteService)
    }

    @Provides
    fun noteNetworkDataSource(noteNetworkService : NoteNetworkService) : NoteNetworkDataSource{
        return NoteNetworkDataSourceImpl(noteNetworkService)
    }

    @Provides
    fun notesInteractors(noteNetworkDataSource: NoteNetworkDataSource) : GetNotes {
        return GetNotes(noteNetworkDataSource)
    }

    @Provides
    fun noteDetailInteractors(noteNetworkDataSource: NoteNetworkDataSource) : UpsertNote {
        return UpsertNote(noteNetworkDataSource)
    }

}