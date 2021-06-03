package com.example.notecalendar.freamwork.presentation.calendarnotes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.business.domain.model.SubNote
import com.example.notecalendar.freamwork.datasource.network.implementation.NoteService
import com.example.notecalendar.freamwork.presentation.calendar.DayViewContainer
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import com.example.notecalendar.freamwork.presentation.noteinput.UpsertNoteFragment
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_calendar_notes.*
import kotlinx.android.synthetic.main.fragment_calendar_notes.view.*
import kotlinx.android.synthetic.main.layout_calendar_header.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CalendarNotesFragment : Fragment(R.layout.fragment_calendar_notes), CalendarDayAdapterListener{

    private val notesAdapter = NotesAdapter()
    private val viewModel by viewModels<CalendarNotesViewModel>()
    private val calendarDayAdapter = CalendarDayAdapter(listener = this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.cv_notes.dayBinder = calendarDayAdapter
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
        view.cv_notes.monthScrollListener = object : MonthScrollListener{
            override fun invoke(month: CalendarMonth) {
                tv_date.text = DateUtils.getDateWithFormat(DateUtils.getDateTime(month), DF.MONTH_YEAR_FORMAT)
                searchNotes(month)
            }
        }
        tv_date.text = DateUtils.getDateWithFormat(DateUtils.getDateTime(currentMonth), DF.MONTH_YEAR_FORMAT)
        view.rv_notes.adapter = notesAdapter
        subscribeObservers()
    }

    private fun searchNotes(month: CalendarMonth){
        val dates = DateUtils.getFirstAndLastDays(month)
        val start = dates[DateUtils.START]
        val end = dates[DateUtils.END]
        viewModel.setStateEvent(CalendarNotesStateEvent.GetNotes(start,end))
    }

    private fun subscribeObservers(){
        setFragmentResultListener(UpsertNoteFragment.UPSERT_RESULT){key, bundle->
            val upsertedNote = bundle.getSerializable(UpsertNoteFragment.UPSERT_NOTE) as Note
        }
        viewModel.viewState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            updateCalendarUI(it.notesWithDate)
        })
    }

    override fun onDaySelected(notes: List<Note>) {
        notesAdapter.updateItems(notes)
    }

    private fun updateCalendarUI(notesWithDate : HashMap<String,ArrayList<Note>>?){
        calendarDayAdapter.updateCalendarNotes(notesWithDate ?: kotlin.run { hashMapOf() })
    }

}