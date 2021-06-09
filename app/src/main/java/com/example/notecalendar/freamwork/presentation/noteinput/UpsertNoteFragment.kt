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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.databinding.FragmentUpsertBinding
import com.example.notecalendar.freamwork.presentation.noteinput.state.UpsertStateEvent
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.util.UUID

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UpsertNoteFragment : Fragment(R.layout.fragment_upsert) {

    companion object{
        const val EDIT_NOTE = "EditNote"
    }

    private val subNoteAdapter = SubNoteAdapter()
    private var selectedDate : DateTime? = null
    private var upsertNoteBinding : FragmentUpsertBinding? = null
    private var editNote : Note? = null

    private val viewModel by viewModels<UpsertNoteViewModel>()

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        upsertNoteBinding = FragmentUpsertBinding.bind(view)
        editNote = arguments?.getSerializable(EDIT_NOTE) as Note?

        upsertNoteBinding?.let { binding->
            binding.rvSubNote.adapter = subNoteAdapter
            binding.tvAddNote.setOnClickListener {
                findNavController().popBackStack()
            }
            binding.btnAddSubnote.setOnClickListener {
                subNoteAdapter.addSubNote()
            }
            binding.btnApply.setOnClickListener {
                controlAndSubmit()
            }
            binding.etDate.inputType = InputType.TYPE_NULL
            binding.etDate.keyListener = null
            binding.etDate.setOnTouchListener(OnTouchListener { v, event ->
                if (MotionEvent.ACTION_UP == event.action) showDatePicker() // Instead of your Toast
                false
            })
            editNote?.let {
                fillEditNote(binding,it)
            }
        }
        subscribeObservers()
    }

    private fun fillEditNote(binding: FragmentUpsertBinding, note : Note){
        binding.etTitle.setText(note.title)
        binding.etDescription.setText(note.description)
        binding.etDate.setText(note.date)
        note.subNotes.forEach { subNote->
            subNoteAdapter.addSubNote(subNote.note, subNote.comment)
        }
        selectedDate = DateUtils.getDateTime(note.date,DF.DATE_FORMAT)
    }

    private fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            it.newNote?.let {
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
                val noteTitle = upsertNoteBinding?.etTitle?.text.toString()
                val noteDescription = upsertNoteBinding?.etDescription?.text.toString()
                val noteDate = DateUtils.getDateWithFormat(selectedDate!!.toDateTime(), DF.DATE_FORMAT)
                val noteId = editNote?.id ?: UUID.randomUUID().toString()
                val note = Note(noteId,noteTitle,noteDescription,subNotesWrapper.subNotes,noteDate)
                viewModel.setStateEvent(UpsertStateEvent.UpsertNote(note))
            }

        }
    }

    private fun isNoteValid() : Boolean{
        return upsertNoteBinding?.let { binding->
            when {
                binding.etTitle.text.isNullOrEmpty() -> {
                    binding.etTitle.error = getString(R.string.note_should_not_be_empty)
                    false
                }
                binding.etDate.text.isNullOrEmpty() && selectedDate != null-> {
                    binding.etDate.error = getString(R.string.date_should_note_be_empty)
                    false
                }
                else -> {
                    binding.etTitle.error = null
                    binding.etDate.error = null
                    true
                }
            }
        }?: kotlin.run { false }
    }

    private fun showDatePicker(){
        val listener = OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            TimePickerDialog(requireContext(), R.style.DatePickerTheme,TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                selectedDate = DateTime().withYear(year).withMonthOfYear(monthOfYear)
                    .withDayOfMonth(dayOfMonth).withHourOfDay(hour).withMinuteOfHour(minute)
                upsertNoteBinding?.etDate?.setText( DateUtils.getDateWithFormat(selectedDate!!.toDateTime(),DF.DATE_FORMAT))
            }, 0, 0, false).show()
        }
        val pickerDate = selectedDate ?: DateTime.now()
        DatePickerDialog(requireContext(), R.style.DatePickerTheme, listener,
            pickerDate.year,pickerDate.monthOfYear,pickerDate.dayOfMonth).show()
    }
}