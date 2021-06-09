package com.example.notecalendar.freamwork.presentation.util

import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.text.SimpleDateFormat
import java.time.YearMonth
import java.util.*


object DateUtils {

    const val START = 0
    const val END = 1

    fun getDateTime(calendarMonth: CalendarMonth) =
        LocalDateTime().withMonthOfYear(calendarMonth.month).withYear(calendarMonth.year).toDateTime()

    fun getDateTime(yearMonth: YearMonth) =
        LocalDateTime().withMonthOfYear(yearMonth.monthValue).withYear(yearMonth.year).toDateTime()

    fun getDateTime(calendarDay: CalendarDay) =
        LocalDateTime.now().withDayOfYear(calendarDay.date.dayOfYear).withMonthOfYear(calendarDay.date.monthValue).
                withYear(calendarDay.date.year).toDateTime()

    fun getFirstAndLastDays(calendarMonth: CalendarMonth) : List<String>{
        val dateTime = getDateTime(calendarMonth)
        val calendar = Calendar.getInstance()
        calendar.time = dateTime.toDate()
        calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE))
        val start = calendar.time
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE))
        val end = calendar.time
        val startDate = getDateWithFormat(DateTime(start), DF.DATE_FORMAT)
        val endDate = getDateWithFormat(DateTime(end), DF.DATE_FORMAT)
        return listOf(startDate,endDate)
    }

    fun getDateWithFormat(dateTime : DateTime, pattern : DF) : String{
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.format(dateTime.toDate())
    }

    fun getDateWithFormat(dateStr : String, pattern : DF, toPattern : DF) : String{
        val simpleDateFormat = SimpleDateFormat(toPattern.format)
        return simpleDateFormat.format(getDate(dateStr, pattern))
    }

    private fun getDate(dateStr : String, pattern: DF) : Date {
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.parse(dateStr)
    }

    fun getDateTime(dateStr: String, pattern: DF) = DateTime(getDate(dateStr,pattern))


}

enum class DF(val format : String){
    HOUR_FORMAT("HH:mm"),
    MONTH_YEAR_FORMAT("YYYY MMM"),
    DATE_FORMAT("dd-MM-yyyy HH:mm"),
    DATE_WITHOUT_HOUR_FORMAT("dd-MM-yyyy"),
    PICKER_FORMAT("dd-MM-yyyy'T'HH:mm.sss"),
    CONVERTER("%s-%s-%s 00:00")
}