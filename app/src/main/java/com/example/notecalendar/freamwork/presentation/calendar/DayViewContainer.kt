package com.example.notecalendar.freamwork.presentation.calendar

import android.view.View
import android.widget.TextView
import com.example.notecalendar.R
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view : View) : ViewContainer(view){
    val day = view.findViewById<TextView>(R.id.tv_day)
}