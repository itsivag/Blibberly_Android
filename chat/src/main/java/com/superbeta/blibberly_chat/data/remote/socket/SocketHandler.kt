package com.superbeta.blibberly_chat.data.remote.socket

import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import io.socket.client.Socket
import kotlinx.coroutines.flow.StateFlow

interface SocketHandler {
    suspend fun connectWithSocketBackend()

    fun getMessageList(): StateFlow<MessageDataModel?>
    fun getUsers(): StateFlow<List<String>>
    fun getSocket(): Socket


    fun registerMessageListener()
    fun registerUsersListener()
    fun registerNewUserConnectedListener()
    fun registerUserDisconnectedListener()

    fun emitMessage(userEmail: String, data: MessageDataModel)
    fun emitUserDisconnected()

    fun disconnectSocket()

}
