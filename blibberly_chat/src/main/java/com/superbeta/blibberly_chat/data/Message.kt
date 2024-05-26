package com.superbeta.blibberly_chat.data

data class Message(
    val messageId: String,
    val content: String,
    val senderID: String,
    val receiverID: String,
    val timeStamp: String,
    val isDelivered: Boolean,
    val isRead: Boolean
)