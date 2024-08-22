package com.superbeta.blibberly_chat.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.data.local.MessageRoomInstanceProvider
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import com.superbeta.blibberly_chat.data.remote.SocketHandlerImpl
import com.superbeta.blibberly_chat.domain.MessagesRepo
import com.superbeta.blibberly_chat.domain.MessagesRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MessageViewModel(private val messagesRepo: MessagesRepo) : ViewModel() {
    private val _messageState = MutableStateFlow<List<MessageDataModel>>(emptyList())
    val messageState: StateFlow<List<MessageDataModel>> = _messageState.asStateFlow()

    private val _usersState = MutableStateFlow<List<SocketUserDataModelItem>>(emptyList())
    val usersState: StateFlow<List<SocketUserDataModelItem>> = _usersState.asStateFlow()

    private val _userProfileState = MutableStateFlow<List<UserDataModel>>(emptyList())
    val userProfileState: StateFlow<List<UserDataModel>> = _userProfileState.asStateFlow()

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
            Log.i("MessageViewModel", "Collecting messages from Viewmodel: $messages")
            _messageState.value = messages
//            Log.i("Collect Message from ViewModel", _messageState.value.toString())
        }
    }


    fun sendMessage(userId: String, data: MessageDataModel) {
        viewModelScope.launch {
            messagesRepo.sendMessage(userId, data)
            _messageState.value += data
        }
    }

    suspend fun getUsers() {
        messagesRepo.getUsers().collect { users ->
            Log.i("MessageViewModel", "Collecting users from Viewmodel: $users")
            _usersState.value = users
        }
    }

    suspend fun getUserProfile() {
        messagesRepo.getUsersProfile(
            listOf(
                SocketUserDataModelItem(
                    userID = "12", username = "sivacbrf2@gmail.com"
                )
            )
        ).collect { liveUserProfiles ->
            _userProfileState.value = liveUserProfiles
            Log.i(
                "MessageViewModel",
                "Collecting live user profile from Viewmodel: $liveUserProfiles"
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val db = MessageRoomInstanceProvider.getDb(application.applicationContext)
                val socketHandlerImpl =
                    SocketHandlerImpl(application.applicationContext.userPreferencesDataStore)

                val messagesRepo = MessagesRepoImpl(db.MessagesDao(), socketHandlerImpl)
                return MessageViewModel(messagesRepo) as T
            }
        }
    }
}