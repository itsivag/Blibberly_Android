package com.superbeta.blibberly_chat.utils

import com.superbeta.blibberly_chat.data.Message
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface SocketHandler {
    fun getMessageList(): StateFlow<ArrayList<Message>>
    fun getSocket(): Socket
    fun sendMessage(data: Message)
    fun registerSocketListener()
    fun disconnectSocket()

}
