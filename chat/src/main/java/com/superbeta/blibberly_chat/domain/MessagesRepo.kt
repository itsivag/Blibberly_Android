package com.superbeta.blibberly_chat.domain

import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import kotlinx.coroutines.flow.StateFlow

interface MessagesRepo {
    suspend fun connectSocketToBackend()
    suspend fun subscribeToMessages()
    suspend fun getMessages(
        currUserEmail: String?,
        receiverEmail: String
    ): StateFlow<List<MessageDataModel>>

    suspend fun sendMessage(message: MessageDataModel)

//    fun getUsers(): StateFlow<List<String>>
//    suspend fun getUsersProfile(liveUsers: List<String>): StateFlow<List<UserDataModel>>

    suspend fun saveMessagesToLocalDb(messages: List<MessageDataModel>)

    //    suspend fun getMessagesFromLocalDb(userId: String)
    fun disconnectUserFromSocket()
//    fun getSpecificUserProfileWithEmail(email: String): UserDataModel?
}