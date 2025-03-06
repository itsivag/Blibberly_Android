package com.superbeta.blibberly_chat.presentation.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.domain.MessagesRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MessageViewModel(private val messagesRepo: MessagesRepo) : ViewModel() {
    private val _messageState = MutableStateFlow<List<MessageDataModel>>(emptyList())
    val messageState: StateFlow<List<MessageDataModel>> = _messageState.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            messagesRepo.subscribeToMessages()
        }
    }

    suspend fun collectMessages(
        currUserEmail: String?,
        receiverEmail: String
    ) {
        viewModelScope.launch(IO) {
            messagesRepo.getMessages(
                currUserEmail = currUserEmail,
                receiverEmail = receiverEmail
            )
                .collect { messages ->
                    Log.i("MessageViewModel", "Collecting messages from Viewmodel: $messages")
                    _messageState.value = messages
                }
        }
    }

    suspend fun sendMessage(data: MessageDataModel) {
        viewModelScope.launch(IO) {
            messagesRepo.sendMessage(data)
        }
    }

    fun disconnectUserFromSocket() {
        messagesRepo.disconnectUserFromSocket()
    }

}