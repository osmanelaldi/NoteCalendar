package com.example.notecalendar.freamwork.datasource.network.implementation

import com.example.notecalendar.business.domain.model.EmptyResponse
import com.example.notecalendar.business.domain.model.Note
import com.squareup.okhttp.ResponseBody
import retrofit2.http.*

interface NoteService {

    suspend fun getMonthlyNotes(startDate: String, endDate: String) = getNotes("gt.$startDate", "lt.$endDate")

    @GET("Notes?select=*")
    suspend fun getNotes(@Query("gt.date") startDate : String, @Query("lt.date") endDate: String) : List<Note>

    @POST("Notes")
    suspend fun upsertNote(@Body note : Note)

    @DELETE("Notes")
    suspend fun removeNote(@Query("id") id : String)
}