package com.example.notecalendar.business.domain.model

import java.io.Serializable

data class Note(
    val id : String,
    var title : String,
    var description : String? = null,
    var subNotes : List<SubNote>,
    var date : String,
    @Transient var isExpanded : Boolean = false
) : Serializable

data class SubNote(
    val note : String,
    val comment : String? = null
)

data class SubNoteBuilderItem(
    var note : String = "",
    var comment: String = "",
    var error : Boolean = false
)

data class SubNotesWrapper(
    val subNotes: List<SubNote>,
    val errors : Boolean
)

class EmptyResponse{}