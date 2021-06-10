package com.example.notecalendar.business.interactors

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertStateEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

@InternalCoroutinesApi
class UpsertNoteTest : BaseTest(){

    private val upsertNote : UpsertNote

    init {
        upsertNote = UpsertNote(noteNetworkDataSource = dependencyContainer.noteNetworkDataSource)
    }

    @Test
    fun addNote_success_confirm() = runBlocking {
        val note = dependencyContainer.noteDataFactory.createNote()
        upsertNote.upsertNote(UpsertStateEvent.UpsertNote(note)).collect {
            val newNote = it?.data?.newNote
            assert(note.id == newNote?.id)
        }
    }
}