package com.example.notecalendar.freamwork.presentation.calendarnotes

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notecalendar.R
import com.example.notecalendar.business.domain.model.Note
import com.example.notecalendar.databinding.FragmentCalendarNotesBinding
import com.example.notecalendar.freamwork.presentation.calendarnotes.state.CalendarNotesStateEvent
import com.example.notecalendar.freamwork.presentation.noteinput.UpsertNoteFragment
import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.ui.MonthScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CalendarNotesFragment : Fragment(R.layout.fragment_calendar_notes), CalendarDayAdapterListener, NoteListener{

    private val notesAdapter = NotesAdapter(this)
    private val viewModel by viewModels<CalendarNotesViewModel>()
    private val calendarDayAdapter = CalendarDayAdapter(listener = this)
    private var calendarNotesBinding : FragmentCalendarNotesBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        calendarNotesBinding = FragmentCalendarNotesBinding.bind(view)
        calendarNotesBinding?.let { binding->
            binding.fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_add_or_edit_note)
            }
            binding.rvNotes.adapter = notesAdapter
        }
        setupCalendar()
        subscribeObservers()
    }

    override fun onDestroyView() {
        calendarNotesBinding = null
        super.onDestroyView()
    }

    private fun setupCalendar(){
        val currentMonth = YearMonth.now()
        val firstMonth = currentMonth.minusMonths(10)
        val lastMonth = currentMonth.plusMonths(10)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        calendarNotesBinding?.let { binding ->
            binding.cvNotes.dayBinder = calendarDayAdapter
            binding.cvNotes.itemAnimator = null
            binding.cvNotes.daySize = CalendarView.sizeAutoWidth(resources.getDimension(R.dimen.dayHeight).toInt())
            binding.cvNotes.setup(firstMonth, lastMonth, firstDayOfWeek)
            binding.cvNotes.scrollToMonth(currentMonth)
            binding.cvNotes.monthScrollListener = object : MonthScrollListener{
                override fun invoke(month: CalendarMonth) {
                    notesAdapter.clear()
                    binding.vwCalendarHeader.tvDate.text = DateUtils.getDateWithFormat(DateUtils.getDateTime(month), DF.MONTH_YEAR_FORMAT)
                    searchNotes(month)
                }
            }
            binding.vwCalendarHeader.tvDate.text = DateUtils.getDateWithFormat(DateUtils.getDateTime(currentMonth), DF.MONTH_YEAR_FORMAT)
            binding.rvNotes.adapter = notesAdapter
        }
    }

    private fun searchNotes(month: CalendarMonth){
        val dates = DateUtils.getFirstAndLastDays(month)
        val start = dates[DateUtils.START]
        val end = dates[DateUtils.END]
        viewModel.setStateEvent(CalendarNotesStateEvent.GetNotes(start,end))
    }

    private fun subscribeObservers(){
        viewModel.viewState.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it.deletedNote?.let { note->
                notesAdapter.deleteItem(note)
            }
            it.notesWithDate?.let { notesWithDate->
                updateCalendarUI(notesWithDate)
            }
        })
    }

    private fun updateCalendarUI(notesWithDate : HashMap<String,ArrayList<Note>>?){
        calendarDayAdapter.updateCalendarNotes(notesWithDate ?: kotlin.run { hashMapOf() })
        calendarNotesBinding?.cvNotes?.notifyCalendarChanged()
    }

    override fun onDaySelected(notes: List<Note>) {
        notesAdapter.updateItems(notes)
    }

    override fun onDelete(note: Note) {
        viewModel.setStateEvent(CalendarNotesStateEvent.DeleteNote(note))
    }

    override fun onEdit(note: Note) {
        val bundle = bundleOf(UpsertNoteFragment.EDIT_NOTE to note)
        findNavController().navigate(R.id.action_add_or_edit_note, bundle)
    }
}