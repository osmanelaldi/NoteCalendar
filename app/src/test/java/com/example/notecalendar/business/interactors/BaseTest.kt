package com.example.notecalendar.business.interactors

import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.di.DependencyContainer
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
open class BaseTest {

    val dependencyContainer : DependencyContainer
    val noteNetworkDataSource : NoteNetworkDataSource

    init {
        dependencyContainer = DependencyContainer()
        dependencyContainer.build()
        noteNetworkDataSource = dependencyContainer.noteNetworkDataSource
    }
}