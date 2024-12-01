package com.superbeta.blibberly_chat.utils

import android.util.Log
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

fun formatTimeStamp(message: MessageDataModel): MessageDataModel {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    inputFormat.timeZone = TimeZone.getTimeZone("UTC")

    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    outputFormat.timeZone = TimeZone.getDefault()

    val date = inputFormat.parse(message.timeStamp)
    val formattedDate = date?.let { outputFormat.format(it) }
    Log.i("DATE FORMATED", formattedDate ?: "NULL")
    return if (formattedDate != null) {
        message.copy(timeStamp = formattedDate)
    } else {
        message
    }
}