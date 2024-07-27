package com.superbeta.blibberly_chat.utils

import android.util.Log
import com.google.gson.Gson
import com.superbeta.blibberly_chat.data.Message
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object SocketHandlerImpl : SocketHandler {
    private lateinit var socket: Socket

    private val _messageList = MutableStateFlow(arrayListOf<Message>())
//    val messageList = _messageList

    init {
        try {
            socket = IO.socket("http://192.168.29.216:3000/")
            socket.connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        registerSocketListener()
    }

    override fun registerSocketListener() {
//        val msgList = arrayListOf<String>()
        socket.on("broadcast") { msg ->
            if (!msg.isNullOrEmpty()) {
                Log.i("Message from server", msg[0].toString() + " size -> " + msg.size)
                val data = Gson().fromJson(msg[0].toString(), Message::class.java)
                _messageList.value = ArrayList(_messageList.value + data)
            }
        }
//        return msgList
    }

    override fun getMessageList(): MutableStateFlow<ArrayList<Message>> {
        return _messageList
    }

    override fun getSocket(): Socket {
        return socket
    }

    override fun sendMessage(data: Message) {
        val dataJson = Gson().toJson(data)
        socket.emit("broadcast", dataJson)
    }

    override fun disconnectSocket() {
        socket.disconnect()
        socket.off()
    }

}