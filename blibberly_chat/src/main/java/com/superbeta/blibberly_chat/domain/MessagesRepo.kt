package com.superbeta.blibberly_chat.domain

import com.superbeta.blibberly_chat.data.model.MessageDataModel
import kotlinx.coroutines.flow.StateFlow

interface MessagesRepo {
    suspend fun subscribeToMessages()
    suspend fun getMessages(): StateFlow<List<MessageDataModel>>
    suspend fun sendMessage(message: MessageDataModel)
    suspend fun saveMessages(messages: List<MessageDataModel>)
}