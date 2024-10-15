package com.superbeta.blibberly_chat.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.data.local.BlibberlyRoomInstanceProvider
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import com.superbeta.blibberly_chat.data.remote.SocketHandlerImpl
import com.superbeta.blibberly_chat.domain.MessagesRepo
import com.superbeta.blibberly_chat.domain.MessagesRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class HomeScreenState {
    LIVE_USERS_RETRIEVAL_SUCCESS,
    LIVE_USERS_RETRIEVAL_LOADING,
    LIVE_USERS_RETRIEVAL_ERROR,
    LIVE_USERS_EMPTY,
    LIVE_USERS_PROFILE_RETRIEVAL_SUCCESS,
    LIVE_USERS_PROFILE_RETRIEVAL_LOADING,
    LIVE_USERS_PROFILE_RETRIEVAL_ERROR,
}

class MessageViewModel(private val messagesRepo: MessagesRepo) : ViewModel() {
    private val _messageState = MutableStateFlow<List<MessageDataModel>>(emptyList())
    val messageState: StateFlow<List<MessageDataModel>> = _messageState.asStateFlow()

    private val _usersState = MutableStateFlow<List<SocketUserDataModelItem>>(emptyList())
    val usersState: StateFlow<List<SocketUserDataModelItem>> = _usersState.asStateFlow()

    private val _userProfileState = MutableStateFlow<List<UserDataModel>>(emptyList())
    val userProfileState: StateFlow<List<UserDataModel>> = _userProfileState.asStateFlow()

    private val _homeScreenState =
        MutableStateFlow<HomeScreenState>(HomeScreenState.LIVE_USERS_RETRIEVAL_LOADING)
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            messagesRepo.subscribeToMessages()
        }

        viewModelScope.launch {
            collectMessages()
        }
    }

    private fun getCurrentUserEmail() {
        viewModelScope.launch {
            messagesRepo.connectSocketToBackend()
        }
    }

    suspend fun collectMessages() {
        messagesRepo.getMessages().collect { messages ->
            Log.i("MessageViewModel", "Collecting messages from Viewmodel: $messages")
            _messageState.value = messages
        }
    }

    fun sendMessage(userId: String, data: MessageDataModel) {
        viewModelScope.launch {
            messagesRepo.sendMessage(userId, data)
            _messageState.value += data
        }
    }

    suspend fun getUsers() {
        _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_LOADING
        try {

            messagesRepo.getUsers().collect { users ->
                Log.i("MessageViewModel", "Collecting users from Viewmodel: $users")
                _usersState.value = users
                if (_usersState.value.isEmpty()) {
                    _homeScreenState.value = HomeScreenState.LIVE_USERS_EMPTY
                } else {
                    _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_SUCCESS
                }
            }
        } catch (e: Exception) {
            _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_ERROR
        }
    }

    suspend fun getUserProfile() {
        viewModelScope.launch {
            if (_homeScreenState.value == HomeScreenState.LIVE_USERS_RETRIEVAL_SUCCESS) {
                _homeScreenState.value = HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_LOADING
                _usersState.collect { liveUsers ->
                    messagesRepo.getUsersProfile(liveUsers).collect { liveUserProfiles ->
                        _userProfileState.value = liveUserProfiles
                        Log.i(
                            "MessageViewModel",
                            "Collecting live user profile from Viewmodel: $liveUserProfiles"
                        )
                        if (_userProfileState.value.isNotEmpty()) {
                            _homeScreenState.value =
                                HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_SUCCESS
                        } else {
                            _homeScreenState.value =
                                HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_ERROR
                        }
                    }
                }
            }
        }
    }

    //    companion object {
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>, extras: CreationExtras
//            ): T {
//                val application =
//                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
//                val db = BlibberlyRoomInstanceProvider.getMessagesDb(application.applicationContext)
//    val socketHandlerImpl =
//        SocketHandlerImpl.getInstance(application.applicationContext.userPreferencesDataStore)
//
//                val messagesRepo = MessagesRepoImpl(db.MessagesDao(), socketHandlerImpl)
//                return MessageViewModel(messagesRepo) as T
//            }
//        }
//    }
}