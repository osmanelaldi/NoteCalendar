package com.example.notecalendar.business.data.network.implementation

import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.freamwork.datasource.network.implementation.NoteService

class NoteNetworkDataSourceImpl(val noteService: NoteService) : NoteNetworkDataSource{
    override suspend fun getNotes(startDate: String, endDate: String): List<Note> {
        return noteService.getNotes(startDate, endDate)

    }

    override suspend fun upsertNote(note: Note): Any {
        return noteService.upsertNote(note)

    }

    override suspend fun removeNote(note: Note): Any {
       return noteService.removeNote(note.id)
    }


}