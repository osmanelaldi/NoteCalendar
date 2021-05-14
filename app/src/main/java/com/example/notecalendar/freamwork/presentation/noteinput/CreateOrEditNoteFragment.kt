package com.example.notecalendar.freamwork.presentation.noteinput

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notecalendar.R
import kotlinx.android.synthetic.main.fragment_create_edit.*

class CreateOrEditNoteFragment : Fragment(R.layout.fragment_create_edit) {

    private val subNoteAdapter = SubNoteAdapter()

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
    }

    private fun controlAndSubmit(){
        val subNotesWrapper = subNoteAdapter.retrieveSubNotes()
    }
}