package com.superbeta.blibberly_chat.utils

import com.superbeta.blibberly_chat.data.Message
import io.socket.client.Socket

interface SocketHandler {
    fun getSocket(): Socket
    fun sendMessage(data: Message)
    fun registerSocketListener()
    fun disconnectSocket()

}
