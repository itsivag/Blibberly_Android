package com.superbeta.blibberly_chat.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly_chat.data.local.MessageRoomInstanceProvider
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModel
import com.superbeta.blibberly_chat.data.remote.SocketHandlerImpl
import com.superbeta.blibberly_chat.domain.MessagesRepo
import com.superbeta.blibberly_chat.domain.MessagesRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessageViewModel(private val messagesRepo: MessagesRepo) : ViewModel() {
    private val _messageState = MutableStateFlow<List<MessageDataModel>>(emptyList())
    val messageState: StateFlow<List<MessageDataModel>> =
        _messageState.asStateFlow()

    init {
        viewModelScope.launch {
            messagesRepo.subscribeToMessages()
        }

        viewModelScope.launch {
            collectMessages()
        }
    }

    suspend fun collectMessages() {
        messagesRepo.getMessages().collect { messages ->
            Log.i("MessageViewModel", "Collecting messages from repository: $messages")
            _messageState.value = messages
            Log.i("Collect Message from ViewModel", _messageState.value.toString())
        }
    }


    fun sendMessage(data: MessageDataModel) {
        viewModelScope.launch {
            messagesRepo.sendMessage(data)
            _messageState.value += data
        }
    }

    fun getUsers(): StateFlow<SocketUserDataModel> {
        return messagesRepo.getUsers()
    }

    suspend fun getNewUserConnected() {
        viewModelScope.launch {
            messagesRepo.getNewUserConnected()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val db = MessageRoomInstanceProvider.getDb(application.applicationContext)
                val messagesRepo = MessagesRepoImpl(db.MessagesDao(), SocketHandlerImpl)

                return MessageViewModel(messagesRepo) as T
            }
        }
    }
}