package com.superbeta.blibberly_chat.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NotificationViewModel : ViewModel() {

    private val broadcast = true

    init {
        viewModelScope.launch {
            Firebase.messaging.subscribeToTopic("chat").await()
        }
    }

    fun sendMessage() {
        viewModelScope.launch {
//            val notificationDto = SendNotificationDto(
//                to = if (broadcast) null else "enter_some_token",
//                notificationBody = NotificationBody("sample title", "sample body")
//            )
//
//            try {
//                if (broadcast) {
//                    api.broadcast(notificationDto)
//                } else {
//                    api.sendNotification(notificationDto)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
        }
    }
}
