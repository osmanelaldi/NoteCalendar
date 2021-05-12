package com.example.notecalendar.business.domain.model

data class Note(
    val id : String,
    val title : String,
    val subNotes : List<SubNote>,
    val date : String,
    var isExpanded : Boolean = false
)

data class SubNote(
    val note : String,
    val comment : String = ""
)