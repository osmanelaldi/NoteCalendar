package com.example.notecalendar.freamwork.presentation.noteinput.state

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.StateEvent

sealed class InsertEditStateEvent : StateEvent{
    data class UpsertNote(val note : Note) : InsertEditStateEvent(){
        override fun errorInfo() = "Error on insert note"
        override fun eventName() = "UpsertNote"
        override fun shouldDisplayProgressBar() = true
    }
}