package com.example.notecalendar.freamwork.presentation.common

import android.app.Activity
import android.widget.Toast
import androidx.annotation.StringRes
import com.example.notecalendar.business.domain.state.StateMessageCallback


fun Activity.displayToast(
    @StringRes message:Int,
    stateMessageCallback: StateMessageCallback
){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    stateMessageCallback.removeMessageFromStack()
}

fun Activity.displayToast(
    message:String,
    stateMessageCallback: StateMessageCallback
){
    Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    stateMessageCallback.removeMessageFromStack()
}


