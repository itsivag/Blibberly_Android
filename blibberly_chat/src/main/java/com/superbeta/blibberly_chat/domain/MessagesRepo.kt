package com.superbeta.blibberly_chat.domain

import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModel
import kotlinx.coroutines.flow.StateFlow

interface MessagesRepo {
    suspend fun subscribeToMessages()
    suspend fun getMessages(): StateFlow<List<MessageDataModel>>
    suspend fun sendMessage(message: MessageDataModel)
    fun getUsers(): StateFlow<SocketUserDataModel>
    suspend fun getNewUserConnected()
    suspend fun saveMessagesToLocalDb(messages: List<MessageDataModel>)
}