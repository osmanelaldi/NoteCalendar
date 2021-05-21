package com.example.notecalendar.freamwork.presentation.calendar

import android.view.View
import android.widget.TextView
import com.example.notecalendar.R
import com.kizitonwose.calendarview.ui.ViewContainer
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.time.temporal.WeekFields
import java.util.*

class MonthViewContainer(view: View) : ViewContainer(view) {
    val monday = view.findViewById<TextView>(R.id.tv_mon)
    val tuesday = view.findViewById<TextView>(R.id.tv_tue)
    val wednesday = view.findViewById<TextView>(R.id.tv_wed)
    val thursday = view.findViewById<TextView>(R.id.tv_thr)
    val friday = view.findViewById<TextView>(R.id.tv_fri)
    val saturday = view.findViewById<TextView>(R.id.tv_sat)
    val sunday = view.findViewById<TextView>(R.id.tv_sun)

    init {
        val days = generateWeek(Locale.ENGLISH)
        //view.setOnClickListener { listener.onDateSelected(month) }
        monday.text = days[0]
        tuesday.text = days[1]
        wednesday.text = days[2]
        thursday.text = days[3]
        friday.text = days[4]
        saturday.text = days[5]
        sunday.text = days[6]
    }
}

fun generateWeek(loc: Locale?) : List<String>{
    val days = arrayListOf<String>()
    val wf: WeekFields = WeekFields.of(loc)
    var day: DayOfWeek = wf.getFirstDayOfWeek()
    for (i in DayOfWeek.values().indices) {
        day = day.plus(1)
        days.add(day.getDisplayName(TextStyle.SHORT, loc).toUpperCase())
    }
    return days
}