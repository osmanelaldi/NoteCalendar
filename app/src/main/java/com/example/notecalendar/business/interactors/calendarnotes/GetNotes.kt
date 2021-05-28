package com.example.notecalendar.business.interactors.calendarnotes

import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.domain.state.StateEvent

class GetNotes(
    private var noteNetworkDataSource: NoteNetworkDataSource
){
    fun getNotes(stateEvent: StateEvent){

    }
}