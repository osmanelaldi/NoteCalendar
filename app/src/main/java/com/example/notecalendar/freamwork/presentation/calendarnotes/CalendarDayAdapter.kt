package com.example.notecalendar.freamwork.presentation.calendarnotes

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.freamwork.presentation.calendar.DayViewContainer
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.DayBinder

class CalendarDayAdapter(var notesWithDates : HashMap<String,ArrayList<Note>> = hashMapOf(),
                         val listener : CalendarDayAdapterListener?) : DayBinder<DayViewContainer> {
    override fun bind(container: DayViewContainer, day: CalendarDay) {
        val date = DateUtils.getDateWithFormat(DateUtils.getDateTime(day), DF.DATE_WITHOUT_HOUR_FORMAT)
        val notes = notesWithDates[date]
        val contains = !notes.isNullOrEmpty()
        updateDay(container.day, contains)
        container.day.text = day.date.dayOfMonth.toString()
        container.day.setOnClickListener {
            val result = if(notes.isNullOrEmpty()) listOf() else notes.toList()
            listener?.onDaySelected(result)
        }
    }

    override fun create(view: View) = DayViewContainer(view)

    private fun updateDay(day : TextView, contains : Boolean){
        day.isActivated = contains
        day.setTextColor(ContextCompat.getColor(day.context, if (contains) R.color.dark else R.color.white))
    }

    fun updateCalendarNotes(notes: HashMap<String, ArrayList<Note>>){
        notesWithDates = notes
    }
}

interface CalendarDayAdapterListener{
    fun onDaySelected(notes : List<Note>)
}