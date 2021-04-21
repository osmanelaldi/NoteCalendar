package com.example.notecalendar

import org.joda.time.DateTime

data class NoteItem (
    val title : String,
    val start : DateTime,
    val end : DateTime
)