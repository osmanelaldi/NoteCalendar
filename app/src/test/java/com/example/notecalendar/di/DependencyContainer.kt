package com.example.notecalendar.di

import com.example.notecalendar.business.data.NoteDataFactory
import com.example.notecalendar.business.data.network.FakeNoteNetworkDataSourceImpl
import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource

class DependencyContainer {
    lateinit var noteDataFactory : NoteDataFactory
    lateinit var noteNetworkDataSource : NoteNetworkDataSource

    fun build(){
        this.javaClass.classLoader?.let {
            noteDataFactory = NoteDataFactory(it)
        }
        noteNetworkDataSource = FakeNoteNetworkDataSourceImpl(
            noteDataFactory.produceHashMapOfNotes(
                noteDataFactory.produceListOfNotes()
            )
        )
    }
}