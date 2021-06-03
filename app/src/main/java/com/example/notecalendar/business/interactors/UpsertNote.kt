package com.example.notecalendar.business.interactors

import com.example.notecalendar.business.data.network.ApiResponseHandler
import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.data.util.safeApiCall
import com.example.notecalendar.business.domain.state.*
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertStateEvent
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpsertNote
@Inject
constructor(
    private val noteNetworkDataSource: NoteNetworkDataSource
) {
    fun upsertNote(stateEvent : StateEvent) : Flow<DataState<UpsertViewState>?> = flow {
        val event = stateEvent as UpsertStateEvent.UpsertNote
        val apiResult = safeApiCall(IO){
            noteNetworkDataSource.upsertNote(event.note)
        }
        val apiResponse = object : ApiResponseHandler<UpsertViewState,Any>(
            response = apiResult,
            stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Any): DataState<UpsertViewState> {
               return DataState.data(
                   response = Response(
                       message = UPSERT_NOTE_SUCCESS,
                       uiComponentType = UIComponentType.Toast(),
                       messageType = MessageType.Success()
                   ),
                   data = UpsertViewState(newNote = event.note),
                   stateEvent
               )
            }
        }.getResult()

        emit(apiResponse)
    }
    companion object{
        val UPSERT_NOTE_SUCCESS = "UPSERT_NOTE_SUCCESS"
        val UPSERT_NOTE_FAILED = "UPSERT_NOTE_FAILED"
    }
}