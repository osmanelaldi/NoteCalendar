package com.example.notecalendar.business.interactors.calendarnotes

import com.example.notecalendar.business.data.network.ApiResponseHandler
import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.data.util.safeApiCall
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.*
import com.example.notecalendar.freamwork.presentation.noteinput.state.InsertEditStateEvent
import com.example.notecalendar.freamwork.presentation.noteinput.state.InsertEditViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpsertNote(
    private val noteNetworkDataSource: NoteNetworkDataSource
) {
    fun createNote(stateEvent : StateEvent) : Flow<DataState<InsertEditViewState>?> = flow {
        val event = stateEvent as InsertEditStateEvent.UpsertNote
        val apiResult = safeApiCall(IO){
            noteNetworkDataSource.upsertNote(event.note)
        }
        val apiResponse = object : ApiResponseHandler<InsertEditViewState,Any>(
            response = apiResult,
            stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Any): DataState<InsertEditViewState> {
               return DataState.data(
                   response = Response(
                       message = INSERT_NOTE_SUCCESS,
                       uiComponentType = UIComponentType.Toast(),
                       messageType = MessageType.Success()
                   ),
                   data = null,
                   stateEvent
               )
            }
        }.getResult()

        emit(apiResponse)
    }
    companion object{
        val INSERT_NOTE_SUCCESS = "Successfully inserted new note."
        val INSERT_NOTE_FAILED = "Failed to insert new note."
    }
}