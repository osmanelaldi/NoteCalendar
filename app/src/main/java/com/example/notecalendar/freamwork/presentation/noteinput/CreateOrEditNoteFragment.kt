package com.example.notecalendar.freamwork.presentation.noteinput

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notecalendar.R
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import kotlinx.android.synthetic.main.fragment_create_edit.*
import org.joda.time.LocalDateTime
import java.util.*


class CreateOrEditNoteFragment : Fragment(R.layout.fragment_create_edit) {

    private val subNoteAdapter = SubNoteAdapter()
    private var selectedDate : LocalDateTime? = null

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
    }

    private fun controlAndSubmit(){
        val subNotesWrapper = subNoteAdapter.retrieveSubNotes()
        if (subNotesWrapper.errors)
            subNoteAdapter.showError()
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