package com.superbeta.blibberly_chat.data.remote.socket

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.PrivateMessage
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SocketHandlerImpl(private val userPreferencesDataStore: DataStore<Preferences>) :
    SocketHandler {
    private lateinit var socket: Socket

    private val _messageList = MutableStateFlow(listOf<MessageDataModel>())
    private val _usersList = MutableStateFlow<List<SocketUserDataModelItem>>(emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            connectWithSocketBackend()
        }
    }

    override suspend fun connectWithSocketBackend() {
        try {
            val options = IO.Options()
            val preferences = userPreferencesDataStore.data.first()
            val email = preferences[stringPreferencesKey("user_email")]

            if (email != null) {
                options.auth = mapOf("username" to email)
                socket = IO.socket("http://192.168.29.216:3000/", options)
                socket.connect()

                registerMessageListener()
                registerUsersListener()
                registerNewUserConnectedListener()
                registerUserDisconnectedListener()
            } else {
                Log.e("SocketHandlerImpl", "Email is null, cannot connect to socket")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun registerMessageListener() {
        try {
            Log.i("Message from server", "Started Listening")
            socket.on("private message") { msg ->
                if (!msg.isNullOrEmpty()) {
                    try {
                        val data = Gson().fromJson(msg[0].toString(), PrivateMessage::class.java)
                        _messageList.value += data.content
                        Log.i("Message from server", _messageList.value.toString())
                    } catch (e: Exception) {
                        Log.e("Invalid Message JSON", e.toString())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun registerUsersListener() {
        try {
            socket.on("users") { users ->
                if (!users.isNullOrEmpty()) {
                    try {
                        val userList = Gson().fromJson(
                            users[0].toString(),
                            Array<SocketUserDataModelItem>::class.java
                        ).toList()
                        Log.i("Connected Users", userList.toString())
                        _usersList.value = userList
                    } catch (e: Exception) {
                        Log.e("Invalid Users JSON", e.toString())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun registerNewUserConnectedListener() {
        try {
            socket.on("user connected") { newUser ->
                if (!newUser.isNullOrEmpty()) {
                    try {
                        val data = Gson().fromJson(
                            newUser[0].toString(),
                            SocketUserDataModelItem::class.java
                        )
                        val newUserList = _usersList.value.toMutableList()
                        newUserList.add(data)
                        _usersList.value = newUserList
                        Log.i("New user connected", data.toString())
                    } catch (e: Exception) {
                        Log.e("Invalid new User JSON", e.toString())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun registerUserDisconnectedListener() {
        try {
            socket.on("user disconnected") { disconnectedUser ->
                if (!disconnectedUser.isNullOrEmpty()) {
                    try {
                        val data = Gson().fromJson(
                            disconnectedUser[0].toString(),
                            SocketUserDataModelItem::class.java
                        )
                        val newUserList = _usersList.value.toMutableList()
                        newUserList.remove(data)
                        _usersList.value = newUserList
                        Log.i("user disconnected list", _usersList.value.toString())
                        Log.i("user disconnected", data.toString())
                    } catch (e: Exception) {
                        Log.e("Invalid disconnected User JSON", e.toString())
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getUsers(): StateFlow<List<SocketUserDataModelItem>> = _usersList.asStateFlow()

    override fun getMessageList(): StateFlow<List<MessageDataModel>> = _messageList.asStateFlow()

    override fun getSocket(): Socket = socket


    override fun emitMessage(userId: String, data: MessageDataModel) {
        val message = mapOf(
            "content" to data,
            "to" to userId
        )
        val jsonMessage = Gson().toJson(message)

        Log.i("private message", jsonMessage)

        socket.emit("private message", jsonMessage)
    }

    override fun emitUserDisconnected() {
        TODO("Not yet implemented")
    }

    override fun disconnectSocket() {
        Log.i("Socket", "DISCONNECTED")
        socket.disconnect()
        socket.off()
    }
}