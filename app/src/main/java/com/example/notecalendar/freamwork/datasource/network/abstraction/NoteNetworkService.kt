package com.example.notecalendar.freamwork.datasource.network.abstraction

import com.example.notecalendar.business.domain.model.Note

interface NoteNetworkService {

    suspend fun getNotes(startDate : String, endDate: String)

    suspend fun addNote(note : Note)

    suspend fun removeNote(id : String)
}