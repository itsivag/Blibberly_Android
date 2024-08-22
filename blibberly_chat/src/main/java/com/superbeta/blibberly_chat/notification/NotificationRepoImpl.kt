package com.superbeta.blibberly_chat.notification

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.tasks.await

class NotificationRepoImpl : NotificationRepo {
    private val firebaseMessaging = Firebase.messaging

    override suspend fun getFCMToken(): String {
        val token = firebaseMessaging.token.await()
        Log.i("FCM Notification Token", token)

        return token
    }


}