package com.superbeta.blibberly_chat.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.superbeta.blibberly_chat.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        Log.i("FCM Notification", "New Token Generated")

        //TODO storing the token in the database on new Token generation
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

//        message.notification?.let { sendNotification(it) }
        sendNotification()
        Log.i("Notification Received", message.toString())
    }

    private fun sendNotification() {
        Log.i("Sending Notification", ".......")

        val channelId = "default_channel_id"
        val channelName = "Default Channel"

        // Create NotificationManager
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create NotificationChannel for Android Oreo and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Create an intent that will be fired when notification is clicked
        // Replace MainActivity::class.java with your main activity
//        val intent = Intent(this, MainActivity::class.java).apply {
//            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//        }

//        val pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            intent,
//            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
//        )

        // Build the notification
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.chat) // Replace with your notification icon
            .setContentTitle("New Message")
            .setContentText("You have a new notification")
            .setAutoCancel(true)
//            .setContentIntent(pendingIntent)

        // Show the notification
        notificationManager.notify(
            System.currentTimeMillis().toInt(), // Use unique ID for each notification
            notificationBuilder.build()
        )
    }


}

