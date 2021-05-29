package com.example.notecalendar.freamwork.presentation.noteinput.state

import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.ViewState
import java.io.Serializable

data class UpsertViewState (
    var newNote : Note? = null
    ) : Serializable, ViewState