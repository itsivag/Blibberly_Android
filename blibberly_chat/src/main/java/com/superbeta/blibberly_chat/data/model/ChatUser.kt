package com.superbeta.blibberly_chat.data.model

data class ChatUser(
    val userId: String,
    val name: String,
    val photoUri: String,
    val isOnline: Boolean
)
