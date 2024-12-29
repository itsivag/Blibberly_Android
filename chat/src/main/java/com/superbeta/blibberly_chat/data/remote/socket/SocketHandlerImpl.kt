package com.superbeta.blibberly_chat.data.remote.socket

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.remote.supabase.ChatRemoteService
import com.superbeta.blibberly_chat.utils.SOCKET_URL
import io.socket.client.Ack
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SocketHandlerImpl(
    private val userPreferencesDataStore: DataStore<Preferences>,
    private val chatRemoteService: ChatRemoteService
) :
    SocketHandler {
    private lateinit var socket: Socket

    private val _messageList = MutableStateFlow<MessageDataModel?>(null)
    private val _usersList = MutableStateFlow<List<String>>(emptyList())

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
//                options.auth = mapOf("username" to email)
                Log.i("SocketHandlerImpl", "Connecting to socket")
                options.extraHeaders = mapOf(
                    "email" to listOf(email),
//                    "username" to listOf("sample_sambavam")
                )

                socket = IO.socket(SOCKET_URL, options)
                socket.connect()

                //req for connected users
                socket.emit(SocketEvents.AllConnectedUsersEvent.eventName, "From Android")

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
            socket.on(SocketEvents.MessageEvent.eventName) { msg ->
                if (!msg.isNullOrEmpty()) {
                    try {
                        val data = Gson().fromJson(msg[0].toString(), MessageDataModel::class.java)
                        _messageList.value = data
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
            socket.on(SocketEvents.AllConnectedUsersEvent.eventName) { users ->
                if (!users.isNullOrEmpty()) {
                    try {
                        val userList = Gson().fromJson(
                            users[0].toString(),
                            Array<String>::class.java
                        ).toList()
                        Log.i("Connected Users", users.toList().toString())
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
            socket.on(SocketEvents.NewUserConnectedEvent.eventName) { newUser ->
                if (!newUser.isNullOrEmpty()) {
                    try {
                        val data = Gson().fromJson(
                            newUser[0].toString(),
                            String::class.java
                        )
                        val newUserList = _usersList.value.toMutableList()
                        newUserList.add(data)
                        _usersList.value = newUserList
                        Log.i("New user connected", _usersList.value.toString())
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
            socket.on(SocketEvents.UsersDisconnectedEvent.eventName) { disconnectedUser ->
                if (!disconnectedUser.isNullOrEmpty()) {
                    try {
                        val data = Gson().fromJson(
                            disconnectedUser[0].toString(),
                            String::class.java
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

    override fun getUsers(): StateFlow<List<String>> = _usersList.asStateFlow()

    override fun getMessageList(): StateFlow<MessageDataModel?> = _messageList.asStateFlow()

    override fun getSocket(): Socket = socket


    override fun emitMessage(data: MessageDataModel) {
        val jsonMessage = Gson().toJson(data)
//        Log.i("SocketHandlerImpl", jsonMessage)
//        socket.emit(SocketEvents.MessageEvent.eventName, jsonMessage)
        socket.emit(SocketEvents.MessageEvent.eventName, jsonMessage, Ack { a ->
            Log.i("SocketHandlerImpl Ack", a.firstOrNull().toString())
            //if no ack is received then save in db
            if (a.firstOrNull() == null) {
                CoroutineScope(Dispatchers.IO).launch {
                    chatRemoteService.saveMessageToRemoteDb(data)
                }
            }
        })
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