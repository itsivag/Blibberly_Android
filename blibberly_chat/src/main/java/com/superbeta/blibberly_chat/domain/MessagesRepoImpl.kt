package com.superbeta.blibberly_chat.domain

import android.util.Log
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.local.MessagesDao
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import com.superbeta.blibberly_chat.data.remote.socket.SocketHandler
import com.superbeta.blibberly_chat.data.remote.supabase.ChatRemoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MessagesRepoImpl(
    private val db: MessagesDao,
    private val socketHandler: SocketHandler,
    private val chatRemoteService: ChatRemoteService
) : MessagesRepo {
    private val _messageState = MutableStateFlow<List<MessageDataModel>>(emptyList())
    private val _liveUserProfilesState = MutableStateFlow<List<UserDataModel>>(emptyList())

//    private val supabaseUsersDb = supabase.from("Users")

    override suspend fun connectSocketToBackend() {
        socketHandler.connectWithSocketBackend()
//        Log.i("SOCKET CONNECT", socketHandler.getSocket().connected().toString())
    }

    override suspend fun subscribeToMessages() {
        socketHandler.getMessageList().collect { messages ->
            _messageState.value = messages
            saveMessagesToLocalDb(_messageState.value)
//            Log.i("Collect Message from server", _messageState.value.toString())
        }
    }

    override suspend fun getMessages(userEmail: String): StateFlow<List<MessageDataModel>> {
        _messageState.value = db.getMessages(userEmail)

        return _messageState.asStateFlow()
    }

    override suspend fun sendMessage(userId: String, message: MessageDataModel) {
        socketHandler.emitMessage(userId, message)
//        _messageState.value += message
        saveMessagesToLocalDb(_messageState.value)
    }

    override fun getUsers(): StateFlow<List<SocketUserDataModelItem>> {
        return socketHandler.getUsers()
    }

    override suspend fun getUsersProfile(liveUsers: List<SocketUserDataModelItem>): StateFlow<List<UserDataModel>> {
        Log.i("live user raw list", liveUsers.toString())
        try {
//            for (email in liveUsers) {
//                val userProfile = supabaseUsersDb.select {
//                    filter {
//                        UserDataModel::email eq email.username
//                    }
//                }.decodeSingle<UserDataModel>()
//                _liveUserProfilesState.value += userProfile
//            }
            val appendProfiles: (UserDataModel) -> Unit = { newProfiles ->
                _liveUserProfilesState.value += newProfiles
            }
            chatRemoteService.getUsersProfile(liveUsers, appendProfiles)
            Log.i("live user", _liveUserProfilesState.value.toString())
        } catch (e: Exception) {
            Log.e("Error getting live user profile", e.toString())
        }

        return _liveUserProfilesState.asStateFlow()
    }

//    override suspend fun getNewUserConnected(): StateFlow<SocketUserDataModelItem?> {
//        return socketHandler.getNewUser()
//    }

    override suspend fun saveMessagesToLocalDb(messages: List<MessageDataModel>) {
        db.saveMessages(messages)
    }

    override fun disconnectUserFromSocket() {
        try {
            socketHandler.disconnectSocket()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}