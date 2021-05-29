package com.example.notecalendar.freamwork.presentation.noteinput

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.DataState
import com.example.notecalendar.business.domain.state.StateEvent
import com.example.notecalendar.business.interactors.UpsertNote
import com.example.notecalendar.freamwork.presentation.common.BaseViewModel
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertStateEvent
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertViewState
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@FlowPreview
@HiltViewModel
class UpsertNoteViewModel
@Inject
constructor(
    private val upsertNote: UpsertNote
) : BaseViewModel<UpsertViewState>(){
    override fun handleNewData(data: UpsertViewState) {
        data.newNote?.let {
            updateNewNote(it)
        }
    }

    override fun setStateEvent(stateEvent: StateEvent) {
        val job : Flow<DataState<UpsertViewState>?> = when(stateEvent){
            is UpsertStateEvent.UpsertNote -> {
                upsertNote.upsertNote(stateEvent)
            }
            else -> {
                emitInvalidStateEvent(stateEvent)
            }
        }
        launchJob(stateEvent,job)
    }

    override fun initNewViewState() = UpsertViewState()

    private fun updateNewNote(note : Note){
        val update = getCurrentViewStateOrNew()
        update.newNote = note
        setViewState(update)
    }
}