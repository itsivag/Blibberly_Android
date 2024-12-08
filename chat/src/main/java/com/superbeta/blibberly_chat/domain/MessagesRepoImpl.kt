package com.superbeta.blibberly_chat.domain

import android.util.Log
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.local.MessagesDao
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.remote.socket.SocketHandler
import com.superbeta.blibberly_chat.data.remote.supabase.ChatRemoteService
import com.superbeta.blibberly_chat.notification.NotificationBody
import com.superbeta.blibberly_chat.notification.NotificationRepo
import com.superbeta.blibberly_chat.notification.SendNotificationDto
import com.superbeta.blibberly_chat.utils.formatTimeStamp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessagesRepoImpl(
    private val db: MessagesDao,
    private val socketHandler: SocketHandler,
    private val chatRemoteService: ChatRemoteService,
    private val notificationService: NotificationRepo
) : MessagesRepo {

    private val _messageState = MutableStateFlow<List<MessageDataModel>>(arrayListOf())
    private val _liveUserProfilesState = MutableStateFlow<List<UserDataModel>>(emptyList())

//    private val supabaseUsersDb = supabase.from("Users")

    override suspend fun connectSocketToBackend() {
        socketHandler.connectWithSocketBackend()
//        Log.i("SOCKET CONNECT", socketHandler.getSocket().connected().toString())
    }

    override suspend fun subscribeToMessages() {
        CoroutineScope(IO).launch {
            socketHandler.getMessageList().collect { message ->
                if (message != null) {
                    _messageState.value += message
                }
                _messageState.value.distinctBy { it.messageId }
                saveMessagesToLocalDb(_messageState.value)
//            Log.i("Collect Message from server", _messageState.value.toString())
            }
        }
    }

    override suspend fun getMessages(
        userEmail: String,
        userId: String?
    ): StateFlow<List<MessageDataModel>> {

        val formattedTimeStamp = db.getMessages(userEmail).map { message ->
            try {
                formatTimeStamp(message)
            } catch (e: Exception) {
                e.printStackTrace()
                message
            }
        }

        _messageState.value = formattedTimeStamp
        return _messageState.asStateFlow()
    }

    override suspend fun sendMessage(userId: String, message: MessageDataModel) {
        CoroutineScope(IO).launch {

            socketHandler.emitMessage(userId, message)

            saveSingleMessageToDb(userId, message)
            val formattedMessage = formatTimeStamp(message)
            _messageState.value += formattedMessage

            Log.i("Message to be sent", _messageState.value.toString())

            val fcmToken = notificationService.getFCMToken()

            notificationService.sendNotification(
                fcmToken = fcmToken,
                notificationBody = SendNotificationDto(
                    to = fcmToken,
                    notificationBody = NotificationBody(
                        title = message.senderEmail,
                        body = message.content
                    )
                )
            )
        }
    }


    override fun getUsers(): StateFlow<List<String>> {
        return socketHandler.getUsers()
    }

    override suspend fun getUsersProfile(liveUsers: List<String>): StateFlow<List<UserDataModel>> {
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