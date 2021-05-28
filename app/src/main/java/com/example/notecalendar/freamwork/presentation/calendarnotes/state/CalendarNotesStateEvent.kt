package com.example.notecalendar.freamwork.presentation.calendarnotes.state

import com.example.notecalendar.business.domain.state.StateEvent

sealed class CalendarNotesStateEvent : StateEvent {
    data class GetNotes(val startDate : String, val endDate : String) : CalendarNotesStateEvent(){
        override fun errorInfo() = "Failed during getting notes"
        override fun eventName() = "GetNotes"
        override fun shouldDisplayProgressBar() = true
    }
}