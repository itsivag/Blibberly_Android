package com.superbeta.blibberly_chat.presentation.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly_chat.data.Message
import com.superbeta.blibberly_chat.utils.SocketHandler
import com.superbeta.blibberly_chat.utils.SocketHandlerImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MessageViewModel(private val socketHandler: SocketHandler) : ViewModel() {
    private var _messageState = MutableStateFlow<List<Message>>(emptyList())
    val messageState: MutableStateFlow<List<Message>> get() = _messageState

    init {
        getMessages()
    }

    private fun getMessages() {
        viewModelScope.launch {
            socketHandler.getMessageList().collect { messages ->
                _messageState.value = messages
            }
        }
    }

    fun sendMessage(data: Message) {
        viewModelScope.launch {
            socketHandler.sendMessage(data)
            // Add the new message to the current list
            _messageState.value = _messageState.value + data
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
//                val db = RoomInstanceProvider.getDb(application.applicationContext)
//                val mUserRepository = MUserRepositoryImpl(db.userLocalDao())

                return MessageViewModel(SocketHandlerImpl) as T
            }
        }
    }
}