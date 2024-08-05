package com.superbeta.blibberly_chat.notification

import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.superbeta.blibberly_chat.utils_chat.supabase
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.tasks.await

class NotificationRepoImpl : NotificationRepo {
    private val firebaseMessaging = Firebase.messaging

    override suspend fun getFCMToken(): String {
        val token = firebaseMessaging.token.await()
        Log.i("FCM Notification Token", token)

        return token
    }

    override fun storeFCMToken(token: String) {
        val email = supabase.auth.currentSessionOrNull()?.user?.email
        Log.i("FCM STORE USER", email.toString())
    }
}