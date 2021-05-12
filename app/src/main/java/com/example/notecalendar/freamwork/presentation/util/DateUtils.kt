package com.example.notecalendar.freamwork.presentation.util

import com.kizitonwose.calendarview.model.CalendarMonth
import org.joda.time.DateTime
import org.joda.time.LocalDateTime
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter


object DateUtils {

    fun getDateTime(calendarMonth: CalendarMonth) =
        DateTime().withMonthOfYear(calendarMonth.month).withYear(calendarMonth.year)


    fun getDateWithFormat(dateTime : DateTime, pattern : DF) : String{
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.format(dateTime.toDate())
    }

    fun getDateWithFormat(dateStr : String, pattern : DF, toPattern : DF) : String{
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.format(getDateTime(dateStr, toPattern))
    }

    fun getDateTime(dateStr : String, pattern: DF) : LocalDateTime {
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        val date = simpleDateFormat.parse(dateStr)
        return LocalDateTime.fromDateFields(date)
    }

}

enum class DF(val format : String){
    HOUR_FORMAT("HH:mm"),
    MONTH_YEAR_FORMAT("YYYY MMM"),
    DATE_FORMAT("dd-mm-yyyy HH:mm")
}