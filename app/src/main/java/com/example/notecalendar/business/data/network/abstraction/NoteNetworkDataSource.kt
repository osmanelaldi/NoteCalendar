package com.example.notecalendar.business.data.network.abstraction

import com.example.notecalendar.business.domain.model.Note

interface NoteNetworkDataSource {

    suspend fun getNotes(startDate : String, endDate: String) : List<Note>

    suspend fun upsertNote(note : Note) : Any
    suspend fun removeNote(note : Note) : Any

}