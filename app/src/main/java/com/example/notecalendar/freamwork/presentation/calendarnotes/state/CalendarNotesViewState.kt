package com.example.notecalendar.freamwork.presentation.calendarnotes.state

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.ViewState
import java.io.Serializable

class CalendarNotesViewState (
    var notes : List<Note>? = null,
    var deletedNote : Note? = null
) : Serializable, ViewState