package com.superbeta.blibberly_chat.data.remote

import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import io.socket.client.Socket
import kotlinx.coroutines.flow.StateFlow

interface SocketHandler {
    suspend fun connectWithSocketBackend()

    fun getMessageList(): StateFlow<List<MessageDataModel>>
    fun getUsers(): StateFlow<List<SocketUserDataModelItem>>
    fun getSocket(): Socket


    fun registerMessageListener()
    fun registerUsersListener()
    fun registerNewUserConnectedListener()
    fun registerUserDisconnectedListener()

    fun emitMessage(userId: String, data: MessageDataModel)
    fun emitUserDisconnected()

    fun disconnectSocket()

}
