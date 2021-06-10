package com.example.notecalendar.business.interactors

import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

@InternalCoroutinesApi
class GetNotesTest : BaseTest(){

    private val getNotes : GetNotes

    init {
        getNotes = GetNotes(noteNetworkDataSource = noteNetworkDataSource)
    }

    @Test
    fun getNotes_success_confirm() = runBlocking {
        val startDate = "01-05-2021 00:00"
        val endDate = "01-07-2021 23:00"
        getNotes.getNotes(CalendarNotesStateEvent.GetNotes(startDate,endDate))
            .collect {
                val notes = it?.data?.notesWithDate
                assert(!notes.isNullOrEmpty())
            }
    }

    @Test
    fun getNotes_failed_confirm() = runBlocking {
        val startDate = "01-08-2021 00:00"
        val endDate = "01-10-2021 23:00"
        getNotes.getNotes(CalendarNotesStateEvent.GetNotes(startDate,endDate))
            .collect {
                val notes = it?.data?.notesWithDate
                assert(notes.isNullOrEmpty())
            }
    }

}