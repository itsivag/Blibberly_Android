package com.superbeta.blibberly_chat.notification

interface NotificationRepo {
    suspend fun getFCMToken(): String
    fun storeFCMToken(token: String)
}