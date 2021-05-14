package com.example.notecalendar.business.domain.model

data class Note(
    val id : String,
    val title : String,
    val description : String? = null,
    val subNotes : List<SubNote>,
    val date : String,
    var isExpanded : Boolean = false
)

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