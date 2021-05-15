package com.example.notecalendar.util

import android.content.ContentValues.TAG
import android.util.Log
import com.example.notecalendar.BuildConfig.DEBUG

var isUnitTest = false

fun printLogD(className: String?, message: String ) {
    if (DEBUG && !isUnitTest) {
        Log.d(TAG, "$className: $message")
    }
    else if(DEBUG && isUnitTest){
        println("$className: $message")
    }
}
