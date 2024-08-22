package com.superbeta.blibberly_chat.data.remote

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.google.gson.Gson
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import io.socket.client.IO
import io.socket.client.IO.Options
import io.socket.client.Socket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SocketHandlerImpl(context: Context) : SocketHandler {
    private lateinit var socket: Socket

    private val _messageList = MutableStateFlow(listOf<MessageDataModel>())
    private val _usersList = MutableStateFlow<List<SocketUserDataModelItem>>(emptyList())

    private val userPreferencesDataStore = context.userPreferencesDataStore


    init {
        try {
            val options = Options()
            CoroutineScope(Dispatchers.IO).launch {
                userPreferencesDataStore.data.collect { preferences ->
                    val email = preferences[stringPreferencesKey("user_email")]
                    options.auth = mapOf("username" to "email222")
                }
            }
            socket = IO.socket("http://192.168.29.216:3000/", options)
            socket.connect()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        registerMessageListener()
        registerUsersListener()
        registerNewUserConnectedListener()
        registerUserDisconnectedListener()
    }

    override fun registerMessageListener() {
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
    }

    override fun registerUsersListener() {
        socket.on("users") { users ->
            if (!users.isNullOrEmpty()) {
                try {
                    val a = Gson().fromJson(
                        users[0].toString(),
                        Array<SocketUserDataModelItem>::class.java
                    ).toList()
                    Log.i("Connected Users", a.toString())
                    _usersList.value = a
                } catch (e: Exception) {
                    Log.e("Invalid Users JSON", e.toString())
                }
            }
        }
    }

    override fun registerNewUserConnectedListener() {
        socket.on("user connected") { newUser ->
            if (!newUser.isNullOrEmpty()) {
                try {
                    val data =
                        Gson().fromJson(newUser[0].toString(), SocketUserDataModelItem::class.java)

                    val newUserList = _usersList.value.toMutableList()
                    newUserList.add(data)
                    _usersList.value = newUserList

                    Log.i("New user connected", data.toString())
                } catch (e: Exception) {
                    Log.e("Invalid new User JSON", e.toString())
                }
            }
        }
    }

    override fun getUsers(): StateFlow<List<SocketUserDataModelItem>> {
        return _usersList.asStateFlow()
    }

    override fun registerUserDisconnectedListener() {
        socket.on("user disconnected") { disconnectedUser ->
            if (!disconnectedUser.isNullOrEmpty()) {
                try {
                    val data =
                        Gson().fromJson(
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
    }

    override fun getMessageList(): StateFlow<List<MessageDataModel>> {
        return _messageList.asStateFlow()
    }


    override fun getSocket(): Socket {
        return socket
    }

    override fun sendMessage(userId: String, data: MessageDataModel) {

        val message = PrivateMessage(data, userId)
        val jsonMessage = Gson().toJson(message)

        Log.i("private message", jsonMessage)
        socket.emit("private message", jsonMessage)
    }

    override fun disconnectSocket() {
        socket.disconnect()
        socket.off()
    }

}

data class PrivateMessage(val content: MessageDataModel, val to: String)
