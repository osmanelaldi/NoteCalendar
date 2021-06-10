package com.example.notecalendar.business.interactors

import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertStateEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

@InternalCoroutinesApi
class DeleteNoteTest : BaseTest(){

    private val deleteNote : DeleteNote

    init {
        deleteNote = DeleteNote(dependencyContainer.noteNetworkDataSource)
    }

    @Test
    fun deleteNote_success_confirm() = runBlocking {
        val note = dependencyContainer.noteDataFactory.createNote()
        deleteNote.deleteNote(CalendarNotesStateEvent.DeleteNote(note)).collect {
            val deletedNote = it?.data?.deletedNote
            assert(note.id == deletedNote?.id)
        }
    }
}