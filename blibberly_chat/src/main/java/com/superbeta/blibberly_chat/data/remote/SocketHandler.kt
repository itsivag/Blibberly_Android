package com.superbeta.blibberly_chat.data.remote

import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import io.socket.client.Socket
import kotlinx.coroutines.flow.StateFlow

interface SocketHandler {
    fun getMessageList(): StateFlow<List<MessageDataModel>>
    fun getSocket(): Socket
    fun sendMessage(data: MessageDataModel)
    fun registerSocketListener()
    fun registerUsersListener()
    fun registerNewUserConnectedListener()
    fun getUsers(): StateFlow<List<SocketUserDataModelItem>>
    fun getNewUser(): StateFlow<SocketUserDataModelItem?>
    fun userDisconnected()
    fun disconnectSocket()

}
