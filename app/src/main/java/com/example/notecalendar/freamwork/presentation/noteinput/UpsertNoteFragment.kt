package com.example.notecalendar.freamwork.presentation.noteinput

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.state.MessageType
import com.example.notecalendar.business.interactors.UpsertNote
import com.example.notecalendar.freamwork.presentation.common.displayToast
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertStateEvent
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_edit.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.joda.time.LocalDateTime
import java.util.UUID

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UpsertNoteFragment : Fragment(R.layout.fragment_create_edit) {

    companion object{
        const val UPSERT_RESULT = "UPSERT_RESULT"
        const val UPSERT_NOTE = "UPSERT_NOTE"
    }

    private val subNoteAdapter = SubNoteAdapter()
    private var selectedDate : LocalDateTime? = null

    private val viewModel by viewModels<UpsertNoteViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_sub_note.adapter = subNoteAdapter
        tv_add_note.setOnClickListener {
            findNavController().popBackStack()
        }
        btn_add_subnote.setOnClickListener {
            subNoteAdapter.addSubNote()
        }
        btn_apply.setOnClickListener {
            controlAndSubmit()
        }
        et_date.inputType = InputType.TYPE_NULL
        et_date.keyListener = null
        et_date.setOnTouchListener(OnTouchListener { v, event ->
            if (MotionEvent.ACTION_UP == event.action) showDatePicker() // Instead of your Toast
            false
        })
        subscribeObservers()
    }

    private fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.newNote?.let {note->
                val bundle = bundleOf(Pair(UPSERT_NOTE, note))
                setFragmentResult(UPSERT_RESULT,bundle)
                findNavController().popBackStack()
            }
        })
        viewModel.stateMessage.observe(viewLifecycleOwner, Observer {
            it?.let { message->
                Toast.makeText(requireContext(),message.response.message,Toast.LENGTH_LONG).show()
                viewModel.clearStateMessage()
            }
        })
    }

    private fun controlAndSubmit(){
        if (isNoteValid()){
            val subNotesWrapper = subNoteAdapter.retrieveSubNotes()
            if (subNotesWrapper.errors)
                subNoteAdapter.showError()
            else{
                val noteTitle = et_title.text.toString()
                val noteDescription = et_description.text.toString()
                val noteDate = et_title.text.toString()
                val noteId = UUID.randomUUID().toString()
                val note = Note(noteId,noteTitle,noteDescription,subNotesWrapper.subNotes,noteDate)
                viewModel.setStateEvent(UpsertStateEvent.UpsertNote(note))
            }

        }
    }

    private fun isNoteValid() : Boolean{
        if(et_title.text.isNullOrEmpty()){
            et_title.error = getString(R.string.note_should_not_be_empty)
            return false
        }
        else if(et_date.text.isNullOrEmpty()){
            et_date.error = getString(R.string.date_should_note_be_empty)
            return false
        }
        else{
            et_title.error = null
            et_date.error = null
            return true
        }
    }

    private fun showDatePicker(){
        val listener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            TimePickerDialog(requireContext(), R.style.DatePickerTheme,TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                selectedDate = LocalDateTime().withYear(year).withMonthOfYear(monthOfYear)
                    .withDayOfMonth(dayOfMonth).withHourOfDay(hour).withMinuteOfHour(minute)
                et_date.setText( DateUtils.getDateWithFormat(selectedDate!!.toDateTime(),DF.DATE_FORMAT))
            }, 0, 0, false).show()
        }
        val pickerDate = selectedDate ?: LocalDateTime.now()
        DatePickerDialog(requireContext(), R.style.DatePickerTheme, listener,
            pickerDate.year,pickerDate.monthOfYear,pickerDate.dayOfMonth).show()
    }
}