package com.example.notecalendar.freamwork.datasource.network.abstraction

import com.example.notecalendar.business.domain.model.Note

interface NoteNetworkService {

    suspend fun getNotes(startDate : String, endDate: String) : List<Note>

    suspend fun upsertNote(note : Note) : Any

    suspend fun removeNote(id : String) : Any
}