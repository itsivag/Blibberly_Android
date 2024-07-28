package com.superbeta.blibberly_chat.data.remote

import android.util.Log
import com.google.gson.Gson
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SocketHandlerImpl : SocketHandler {
    private lateinit var socket: Socket

    private val _messageList = MutableStateFlow(arrayListOf<MessageDataModel>())

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
        Log.i("Message from server", "Started Listening")
        socket.on("broadcast") { msg ->
            if (!msg.isNullOrEmpty()) {
                try {
                    val data = Gson().fromJson(msg[0].toString(), MessageDataModel::class.java)
                    _messageList.value += data
                    Log.i("Message from server", _messageList.value.toString())
                } catch (e: Exception) {
                    Log.e("Invalid JSON", e.toString())
                }
            }
        }
    }

    override fun getMessageList(): StateFlow<ArrayList<MessageDataModel>> {
        return _messageList.asStateFlow()
    }

    override fun getSocket(): Socket {
        return socket
    }

    override fun sendMessage(data: MessageDataModel) {
        val dataJson = Gson().toJson(data)
        socket.emit("broadcast", dataJson)
    }

    override fun disconnectSocket() {
        socket.disconnect()
        socket.off()
    }

}