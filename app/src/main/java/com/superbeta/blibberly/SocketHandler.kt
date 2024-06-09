package com.superbeta.blibberly

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket

object SocketHandler {
    private lateinit var socket: Socket

    init {
        try {
            socket = IO.socket("http://192.168.29.216:3000/")
            socket.connect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        registerSocketListener()
    }

    private fun registerSocketListener() {
        socket.on("broadcast") { msg ->
            if (!msg.isNullOrEmpty()) {
                Log.i("Message from server", msg[0].toString())
            }
        }
    }

    fun getSocket(): Socket {
        return socket
    }

    fun disconnectSocket() {
        socket.disconnect()
        socket.off()
    }

    fun emitChat() {
        socket.emit("broadcast", "message from client 1")
    }
}