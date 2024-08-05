package com.superbeta.blibberly_chat.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NotificationViewModel : ViewModel() {
    private val api: FCMApi = Retrofit.Builder().baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(GsonConverterFactory.create()).build().create()

    val broadcast = true

    fun sendMessage() {
        viewModelScope.launch {
            val notificationDto = SendNotificationDto(
                to = if (broadcast) null else "enter_some_token",
                notificationBody = NotificationBody("sample title", "sample body")
            )

            try {
                if (broadcast) {
                    api.broadcast(notificationDto)
                } else {
                    api.sendNotification(notificationDto)
                }
            } catch (e: Exception) {

            }
        }
    }
}
