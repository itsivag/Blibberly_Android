package com.superbeta.blibberly_chat.notification

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NotificationRepoImpl : NotificationRepo {
    private val firebaseMessaging = Firebase.messaging
    private val broadcast = true

    private val api: FCMApi = Retrofit.Builder().baseUrl("http://192.168.29.216:8080/")
        .addConverterFactory(GsonConverterFactory.create()).build().create()

    override suspend fun getFCMToken(): String {
        val token = firebaseMessaging.token.await()
        Log.i("FCM Notification Token", token)

        return token
    }

    override suspend fun sendNotification(
        fcmToken: String,
        notificationBody: SendNotificationDto
    ) {
        val notificationDto = SendNotificationDto(
            to = if (broadcast) null else fcmToken,
            notificationBody = NotificationBody("sample title", "sample body")
        )
        try {
            if (broadcast) {
                api.broadcast(notificationDto)
            } else {
                api.sendNotification(notificationDto)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}