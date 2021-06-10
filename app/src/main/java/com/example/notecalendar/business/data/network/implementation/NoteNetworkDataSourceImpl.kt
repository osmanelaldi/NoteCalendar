package com.example.notecalendar.business.data.network.implementation

import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.freamwork.datasource.network.abstraction.NoteNetworkService
import com.example.notecalendar.freamwork.datasource.network.implementation.NoteService

class NoteNetworkDataSourceImpl(private val noteNetworkService: NoteNetworkService) : NoteNetworkDataSource{
    override suspend fun getNotes(startDate: String, endDate: String): List<Note> {
        return noteNetworkService.getNotes("gt.$startDate","lt.$endDate")

    }

    override suspend fun upsertNote(note: Note): Any {
        return noteNetworkService.upsertNote(note)

    }

    override suspend fun removeNote(note: Note): Any {
       return noteNetworkService.removeNote("eq.${note.id}")
    }


}