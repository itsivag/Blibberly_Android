package com.superbeta.blibberly_chat.notification

interface NotificationRepo {
    suspend fun getFCMToken(): String
    suspend fun sendNotification(fcmToken: String, notificationBody: SendNotificationDto)
//    fun storeFCMToken(token: String)
}