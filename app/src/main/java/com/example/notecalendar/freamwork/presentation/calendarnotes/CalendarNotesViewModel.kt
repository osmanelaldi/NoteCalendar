package com.example.notecalendar.freamwork.presentation.calendarnotes

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.DataState
import com.example.notecalendar.business.domain.state.StateEvent
import com.example.notecalendar.business.interactors.DeleteNote
import com.example.notecalendar.business.interactors.GetNotes
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesViewState
import com.example.notecalendar.freamwork.presentation.common.BaseViewModel
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import java.util.ArrayList
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
        data.notesWithDate?.let { updateNotes(it) }
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
        val date = DateUtils.getDateWithFormat(note.date, DF.DATE_FORMAT,DF.DATE_WITHOUT_HOUR_FORMAT)
        update.notesWithDate?.let {notesWithDates->
            notesWithDates[date]?.let { notes->
                val temp = notes.filter { it.id != note.id }
                notesWithDates[date] = temp as ArrayList<Note>
            }
        }
        update.deletedNote = note
        setViewState(update)
    }

    private fun updateNotes(notesWithDate : HashMap<String, ArrayList<Note>>){
        val update = getCurrentViewStateOrNew()
        update.notesWithDate = notesWithDate
        setViewState(update)
    }
}