package com.superbeta.blibberly_chat.notification

data class SendNotificationDto(
    val to: String?,
    val notificationBody: NotificationBody
)

data class NotificationBody(
    val title: String,
    val body: String
)

