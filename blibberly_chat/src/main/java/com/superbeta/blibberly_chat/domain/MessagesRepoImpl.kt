package com.superbeta.blibberly_chat.domain

import android.util.Log
import com.superbeta.blibberly_chat.data.local.MessagesDao
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModel
import com.superbeta.blibberly_chat.data.remote.SocketHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MessagesRepoImpl(private val db: MessagesDao, private val socketHandler: SocketHandler) :
    MessagesRepo {
    private val _messageState = MutableStateFlow<List<MessageDataModel>>(emptyList())

    override suspend fun subscribeToMessages() {
        socketHandler.getMessageList().collect { messages ->
//            _messageState.value = messages
            saveMessagesToLocalDb(messages)
            Log.i("Collect Message from server", _messageState.value.toString())
        }
    }

    override suspend fun getMessages(): StateFlow<List<MessageDataModel>> {
        return _messageState.asStateFlow()
    }

    override suspend fun sendMessage(message: MessageDataModel) {
        socketHandler.sendMessage(message)
        _messageState.value += message
    }

    override fun getUsers(): StateFlow<SocketUserDataModel> {
        return socketHandler.getUsers()
    }

    override suspend fun getNewUserConnected() {
        socketHandler.registerNewUserConnectedListener()
    }

    override suspend fun saveMessagesToLocalDb(messages: List<MessageDataModel>) {
        db.saveMessages(messages)
        _messageState.value = db.getMessages()
    }
}