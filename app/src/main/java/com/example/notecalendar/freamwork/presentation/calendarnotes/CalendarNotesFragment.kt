package com.example.notecalendar.freamwork.presentation.calendarnotes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.model.SubNote
import com.example.notecalendar.freamwork.presentation.calendar.DayViewContainer
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthScrollListener
import kotlinx.android.synthetic.main.fragment_calendar_notes.view.*
import kotlinx.android.synthetic.main.layout_calendar_header.*
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class CalendarNotesFragment : Fragment(R.layout.fragment_calendar_notes){

    private val notesAdapter = NotesAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.cv_notes.dayBinder = object : DayBinder<DayViewContainer>{
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day.text = day.date.dayOfMonth.toString()
            }
            override fun create(view: View) = DayViewContainer(view)

        }
        view.cv_notes.monthScrollListener = object : MonthScrollListener{
            override fun invoke(month: CalendarMonth) {
                Log.d("MONTH","${month.month} - ${month.year}")
                tv_date.text = DateUtils.getDateWithFormat(DateUtils.getDateTime(month), DF.MONTH_YEAR_FORMAT)
            }
        }
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        view.cv_notes.itemAnimator = null
        view.cv_notes.daySize = CalendarView.sizeAutoWidth(resources.getDimension(R.dimen.dayHeight).toInt())
        view.cv_notes.setup(firstMonth, lastMonth, firstDayOfWeek)
        view.cv_notes.scrollToMonth(currentMonth)
        view.fab_add.setOnClickListener {
            findNavController().navigate(R.id.action_add_or_edit_note)
        }

        view.rv_notes.adapter = notesAdapter
        notesAdapter.updateItems(dummy())
    }

    fun dummy() : List<Note>{
        return listOf(
            Note("1","Sınav","Matematik Sınavı",listOf(
                SubNote("Üniteleri bul","yardım al birinden"),
                SubNote("Konulara çalış")
            ),"21-01-2020 16:00"),
            Note("2","Sınav Günü","Amfi katında",listOf(
                SubNote("Kahvaltı yap"),
                SubNote("Yola çık","Metrobüsler boş")
            ),"22-01-2020 08:00")
        )
    }
}