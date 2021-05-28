package com.example.notecalendar.freamwork.datasource.network.implementation

import com.example.notecalendar.business.domain.model.EmptyResponse
import com.example.notecalendar.business.domain.model.Note
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NoteService {

    suspend fun getMonthlyNotes(startDate: String, endDate: String) = getNotes("gt.$startDate", "lt.$endDate")

    @GET("select=*")
    suspend fun getNotes(@Query("date") startDate : String, @Query("date") endDate: String) : List<Note>

    @POST
    suspend fun upsertNote(note : Note) : Any

    @DELETE
    suspend fun removeNote(@Query("id") id : String) : Any
}