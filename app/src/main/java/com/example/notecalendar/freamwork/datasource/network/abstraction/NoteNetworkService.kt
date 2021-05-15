package com.example.notecalendar.freamwork.datasource.network.abstraction

import com.example.notecalendar.business.domain.model.Note

interface NoteNetworkService {

    fun getNotes(startDate : String, endDate: String)

    fun addNote(note : Note)

    fun removeNote(note : Note)
}