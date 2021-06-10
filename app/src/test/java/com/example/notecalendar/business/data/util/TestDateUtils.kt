package com.example.notecalendar.business.data.util

import com.example.notecalendar.freamwork.presentation.util.DF
import java.text.SimpleDateFormat
import java.util.*

object TestDateUtils {

    fun checkBetweenDays(start : String, end : String, checkDate : String) : Boolean{
        val startDate = getDate(start,DF.DATE_FORMAT)
        val endDate = getDate(end,DF.DATE_FORMAT)
        val date = getDate(checkDate,DF.DATE_FORMAT)
        return date in startDate..endDate
    }

    private fun getDate(dateStr : String, pattern: DF) : Date {
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.parse(dateStr)
    }

}

enum class TestDF(val format : String){
    DATE_FORMAT("dd-MM-yyyy HH:mm"),
    DATE_WITHOUT_HOUR_FORMAT("dd-MM-yyyy"),
}