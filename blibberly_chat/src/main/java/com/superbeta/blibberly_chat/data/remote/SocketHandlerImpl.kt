package com.superbeta.blibberly_chat.data.remote

import android.util.Log
import com.google.gson.Gson
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModel
import io.socket.client.IO
import io.socket.client.IO.Options
import io.socket.client.Socket
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object SocketHandlerImpl : SocketHandler {
    private lateinit var socket: Socket

    private val _messageList = MutableStateFlow(listOf<MessageDataModel>())

    init {
        try {

            val options = Options()
            options.auth = mapOf("username" to "sivacbrf2@gmail.com")
            socket = IO.socket("http://192.168.29.216:3000/", options)
            socket.connect()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        registerSocketListener()
        getConnectedUsers()
    }

    override fun registerSocketListener() {
        Log.i("Message from server", "Started Listening")
        socket.on("broadcast") { msg ->
            if (!msg.isNullOrEmpty()) {
                try {
                    val data = Gson().fromJson(msg[0].toString(), MessageDataModel::class.java)
                    _messageList.value += data
                    Log.i("Message from server", _messageList.value.toString())
//                    Log.i("Message from server 2", data.toString())
                } catch (e: Exception) {
                    Log.e("Invalid Message JSON", e.toString())
                }
            }
        }
    }

    override fun getConnectedUsers() {
        socket.on("users") { users ->
            if (!users.isNullOrEmpty()) {
                try {
                    val data = Gson().fromJson(users[0].toString(), SocketUserDataModel::class.java)
                    Log.i("Connected Users", data.toString())
//                    Log.i("Message from server 2", data.toString())
                } catch (e: Exception) {
                    Log.e("Invalid Users JSON", e.toString())
                }
            }
        }
    }

    override fun getMessageList(): StateFlow<List<MessageDataModel>> {
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