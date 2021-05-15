package com.example.notecalendar.business.data.network.abstraction

import com.example.notecalendar.business.domain.model.Note

interface NoteNetworkDataSource {

    fun getNotes(startDate : String, endDate: String)

    fun addNote(note : Note)

    fun removeNote(note : Note)

}