package com.example.notecalendar.business.data.util

import com.example.notecalendar.freamwork.presentation.util.DF
import com.example.notecalendar.freamwork.presentation.util.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TestDateUtils {

    fun checkBetweenDays(start : String, end : String, checkDate : String) : Boolean{
        val startDate = getDate(start,TestDF.DATE_FORMAT)
        val endDate = getDate(end,TestDF.DATE_FORMAT)
        val date = getDate(checkDate,TestDF.DATE_WITHOUT_HOUR_FORMAT)
        return date in startDate..endDate
    }

    private fun getDate(dateStr : String, pattern: TestDF) : Date {
        val simpleDateFormat = SimpleDateFormat(pattern.format)
        return simpleDateFormat.parse(dateStr)
    }

    fun getDateWithFormat(dateStr : String, pattern : TestDF, toPattern : TestDF) : String{
        val simpleDateFormat = SimpleDateFormat(toPattern.format)
        return simpleDateFormat.format(getDate(dateStr, pattern))
    }

}

enum class TestDF(val format : String){
    DATE_FORMAT("dd-MM-yyyy HH:mm"),
    DATE_WITHOUT_HOUR_FORMAT("dd-MM-yyyy"),
}