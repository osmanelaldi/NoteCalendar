package com.example.notecalendar.business.interactors

import com.example.notecalendar.business.data.network.ApiResponseHandler
import com.example.notecalendar.business.data.network.abstraction.NoteNetworkDataSource
import com.example.notecalendar.business.data.util.safeApiCall
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.*
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteNote
@Inject
constructor(private val noteNetworkDataSource: NoteNetworkDataSource){

    fun deleteNote(stateEvent: StateEvent) : Flow<DataState<CalendarNotesViewState>?> = flow {
        val event = stateEvent as CalendarNotesStateEvent.DeleteNote
        val apiResult = safeApiCall(IO){
            noteNetworkDataSource.removeNote(stateEvent.note)
        }

        val apiResponse = object : ApiResponseHandler<CalendarNotesViewState, Any>(
            response = apiResult,
            stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Any): DataState<CalendarNotesViewState>? {
                return DataState.data(
                    response = Response(
                        message = DELETE_NOTE_SUCCESS,
                        messageType = MessageType.Success(),
                        uiComponentType = UIComponentType.Toast()
                    ),
                    data = CalendarNotesViewState(deletedNote = event.note),
                    stateEvent
                )
            }
        }.getResult()
        emit(apiResponse)

    }
    companion object{
        val DELETE_NOTE_SUCCESS = "Successfully deleted the note."
        val DELETE_NOTE_FAILED = "Failed to delete the note."
    }

}