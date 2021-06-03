package com.example.notecalendar.freamwork.datasource.network.implementation

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.freamwork.datasource.network.abstraction.NoteNetworkService
import javax.inject.Inject

class NoteNetworkServiceImpl @Inject constructor(val noteService: NoteService) : NoteNetworkService{
    override suspend fun getNotes(startDate: String, endDate: String): List<Note> {
        return noteService.getNotes(startDate, endDate)
    }

    override suspend fun upsertNote(note: Note) {
        noteService.upsertNote(note)
    }

    override suspend fun removeNote(id: String) {
        noteService.removeNote(id)
    }

}