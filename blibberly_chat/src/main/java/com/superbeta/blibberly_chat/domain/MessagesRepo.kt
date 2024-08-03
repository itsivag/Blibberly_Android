package com.superbeta.blibberly_chat.domain

import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import kotlinx.coroutines.flow.StateFlow

interface MessagesRepo {
    suspend fun subscribeToMessages()
    suspend fun getMessages(): StateFlow<List<MessageDataModel>>
    suspend fun sendMessage(message: MessageDataModel)
    fun getUsers(): StateFlow<List<SocketUserDataModelItem>>
    suspend fun getNewUserConnected(): StateFlow<SocketUserDataModelItem?>
    suspend fun saveMessagesToLocalDb(messages: List<MessageDataModel>)
}