package com.example.notecalendar.business.interactors

import com.example.notecalendar.business.data.network.ApiResponseHandler
import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.data.util.safeApiCall
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.DataState
import com.example.notecalendar.business.domain.state.StateEvent
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNotes
@Inject
constructor(
    private var noteNetworkDataSource: NoteNetworkDataSource
){
    fun getNotes(stateEvent: StateEvent) : Flow<DataState<CalendarNotesViewState>?> = flow {
        val event = stateEvent as CalendarNotesStateEvent.GetNotes
        val apiResult = safeApiCall(IO){
            noteNetworkDataSource.getNotes(event.startDate,event.endDate)
        }
        val apiResponse = object : ApiResponseHandler<CalendarNotesViewState, List<Note>>(
            response = apiResult,
            stateEvent
        ){
            override suspend fun handleSuccess(resultObj: List<Note>): DataState<CalendarNotesViewState>? {
                return DataState.data(
                    data = CalendarNotesViewState(notes = resultObj),
                    response = null,
                    stateEvent = stateEvent
                )
            }
        }.getResult()
        emit(apiResponse)
    }
}