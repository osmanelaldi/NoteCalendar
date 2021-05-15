package com.example.notecalendar.business.domain.state

interface StateEvent {

    fun errorInfo(): String

    fun eventName(): String

    fun shouldDisplayProgressBar(): Boolean
}