package com.example.notecalendar.business.data.network

import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.data.util.TestDateUtils
import com.example.notecalendar.business.domain.model.Note

class FakeNoteNetworkDataSourceImpl
constructor(
    private val notesData : HashMap<String,ArrayList<Note>>,
) : NoteNetworkDataSource{
    override suspend fun getNotes(startDate: String, endDate: String): List<Note> {
        val temp = arrayListOf<Note>()
        notesData.entries.forEach {
            if (TestDateUtils.checkBetweenDays(startDate,endDate,it.key))
                temp.addAll(it.value)
        }
        return temp
    }

    override suspend fun upsertNote(note: Note): Any {
        notesData[note.date]?.let { notes->
            notes.find { it.id == note.id }?.let { foundNote->
                foundNote.date =  note.date
                foundNote.subNotes = note.subNotes
                foundNote.description = note.description
                foundNote.title = note.title
            }?: kotlin.run {
                notes.add(note)
            }
        }?: kotlin.run {
            notesData.put(note.date, arrayListOf())
        }
        return true
    }

    override suspend fun removeNote(note: Note): Any {
        notesData[note.date]?.let {notes->
            notes.removeIf { it.id == note.id }
        }
        return true
    }
}