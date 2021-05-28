package com.example.notecalendar.freamwork.presentation.noteinput.state

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.ViewState
import java.io.Serializable

data class InsertEditViewState (
    var newNote : Note
    ) : Serializable, ViewState