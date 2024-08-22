package com.superbeta.blibberly_chat.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.i("FCM Notification", "New Token Generated")

        //TODO storing the token in the database on new Token generation
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Log.i("Notification Received", message.notification?.title.toString())
    }


}