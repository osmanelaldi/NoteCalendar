package com.example.notecalendar.business.data

import com.example.notecalendar.business.data.util.TestDF
import com.example.notecalendar.business.data.util.TestDateUtils
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.freamwork.presentation.util.DF
import com.google.common.reflect.TypeToken
import com.google.gson.Gson

class NoteDataFactory(
    private val testClassLoader: ClassLoader
){
    fun produceListOfNotes(): List<Note>{
        val notes: List<Note> = Gson()
            .fromJson(
                getNotesFromFile("notes.json"),
                object: TypeToken<List<Note>>() {}.type
            )
        return notes
    }

    fun produceHashMapOfNotes(notes : List<Note>) : HashMap<String,ArrayList<Note>>{
        val map = HashMap<String, ArrayList<Note>>()
        notes.forEach { note->
            val date = TestDateUtils.getDateWithFormat(note.date, TestDF.DATE_FORMAT,TestDF.DATE_WITHOUT_HOUR_FORMAT)
            map[date]?.let {
                it.add(note)
            }?: kotlin.run {
                map.put(date, arrayListOf(note))
            }
        }
        return map
    }

    fun getNotesFromFile(fileName: String): String {
        return testClassLoader.getResource(fileName).readText()
    }

    fun createNote() = Note(id = "1", title = "Test",date = "02.06.2021 19:00",subNotes = listOf())
}