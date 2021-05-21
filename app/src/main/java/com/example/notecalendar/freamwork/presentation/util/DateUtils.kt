package com.example.notecalendar.freamwork.presentation.util

import com.kizitonwose.calendarview.model.CalendarMonth
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*


object DateUtils {

    fun getDateTime(calendarMonth: CalendarMonth) =
        DateTime().withMonthOfYear(calendarMonth.month).withYear(calendarMonth.year)


    fun getDateWithFormat(dateTime : DateTime, pattern : DF) : String{
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.format(dateTime.toDate())
    }

    fun getDateWithFormat(dateStr : String, pattern : DF, toPattern : DF) : String{
        val simpleDateFormat = SimpleDateFormat(toPattern.format)
        return simpleDateFormat.format(getDateTime(dateStr, pattern))
    }

    fun getDateTime(dateStr : String, pattern: DF) : Date {
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.parse(dateStr)
    }

}

enum class DF(val format : String){
    HOUR_FORMAT("HH:mm"),
    MONTH_YEAR_FORMAT("YYYY MMM"),
    DATE_FORMAT("dd-MM-yyyy HH:mm"),
    PICKER_FORMAT("dd-MM-yyyy'T'HH:mm.sss")
}