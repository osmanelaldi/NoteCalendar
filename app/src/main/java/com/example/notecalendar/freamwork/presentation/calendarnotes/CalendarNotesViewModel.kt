package com.example.notecalendar.freamwork.presentation.calendarnotes

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.DataState
import com.example.notecalendar.business.domain.state.StateEvent
import com.example.notecalendar.business.interactors.DeleteNote
import com.example.notecalendar.business.interactors.GetNotes
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesViewState
import com.example.notecalendar.freamwork.presentation.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class CalendarNotesViewModel
@Inject
constructor(
    private val getNotes: GetNotes,
    private val deleteNote: DeleteNote
) : BaseViewModel<CalendarNotesViewState>(){
    override fun handleNewData(data: CalendarNotesViewState) {
        data.notes?.let { updateNotes(it) }
        data.deletedNote?.let { updateDeletedNote(it) }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job : Flow<DataState<CalendarNotesViewState>?> = when(stateEvent){
            is CalendarNotesStateEvent.GetNotes -> {
                getNotes.getNotes(stateEvent)
            }
            is CalendarNotesStateEvent.DeleteNote -> {
                deleteNote.deleteNote(stateEvent)
            }
            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent, job)
    }

    override fun initNewViewState() = CalendarNotesViewState()

    private fun updateDeletedNote(note : Note){
        val update = getCurrentViewStateOrNew()
        update.deletedNote = note
        setViewState(update)
    }

    private fun updateNotes(notes : List<Note>){
        val update = getCurrentViewStateOrNew()
        update.notes = notes
        setViewState(update)
    }
}