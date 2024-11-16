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
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

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

    override suspend fun getMessages(
        userEmail: String,
        userId: String?
    ): StateFlow<List<MessageDataModel>> {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")

        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        outputFormat.timeZone = TimeZone.getDefault()

        val formattedTimeStamp = db.getMessages(userEmail).map { message ->
            try {
                val date = inputFormat.parse(message.timeStamp)
                val formattedDate = date?.let { outputFormat.format(it) }
                Log.i("DATE FORMATED", formattedDate ?: "NULL")
                if (formattedDate != null) {
                    message.copy(timeStamp = formattedDate)
                } else {
                    message
                }
            } catch (e: Exception) {
                e.printStackTrace()
                message
            }
        }
        _messageState.value = formattedTimeStamp
        return _messageState.asStateFlow()
    }

    override suspend fun sendMessage(userId: String, message: MessageDataModel) {
        socketHandler.emitMessage(userId, message)
//        _messageState.value += message
//        saveMessagesToLocalDb(_messageState.value)
        saveSingleMessageToDb(userId, message)
        Log.i("Message to be sent", _messageState.value.toString())

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

    private suspend fun saveSingleMessageToDb(userId: String, message: MessageDataModel) {
        db.saveSingleMessage(message)
    }

    override fun disconnectUserFromSocket() {
        try {
            socketHandler.disconnectSocket()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getSpecificUserProfileWithEmail(email: String): UserDataModel? {
        return _liveUserProfilesState.value.firstOrNull { profile -> profile.email == email }
    }
}