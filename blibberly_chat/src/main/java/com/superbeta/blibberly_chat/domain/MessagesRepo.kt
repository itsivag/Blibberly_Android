package com.superbeta.blibberly_chat.domain

import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import kotlinx.coroutines.flow.StateFlow

interface MessagesRepo {
    suspend fun connectSocketToBackend()
    suspend fun subscribeToMessages()
    suspend fun getMessages(): StateFlow<List<MessageDataModel>>
    suspend fun sendMessage(userId: String, message: MessageDataModel)
    fun getUsers(): StateFlow<List<SocketUserDataModelItem>>
    suspend fun getUsersProfile(liveUsers: List<SocketUserDataModelItem>): StateFlow<List<UserDataModel>>
    suspend fun saveMessagesToLocalDb(messages: List<MessageDataModel>)
}