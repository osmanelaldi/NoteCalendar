package com.example.notecalendar.freamwork.datasource.network.implementation

import com.example.notecalendar.business.domain.model.EmptyResponse
import com.example.notecalendar.business.domain.model.Note
import com.squareup.okhttp.ResponseBody
import retrofit2.http.*

interface NoteService {

    @GET("Notes?")
    suspend fun getNotes(@Query("date") startDate : String, @Query("date") endDate: String, @Query("select") notes : String = "*") : List<Note>

    @POST("Notes")
    suspend fun upsertNote(@Body note : Note)

    @DELETE("Notes")
    suspend fun removeNote(@Query("id") id : String)
}