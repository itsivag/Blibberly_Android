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

//    private val _usersState = MutableStateFlow<List<String>>(emptyList())
//    val usersState: StateFlow<List<String>> = _usersState.asStateFlow()

//    private val _userProfileState = MutableStateFlow<List<UserDataModel>>(emptyList())
//    val userProfileState: StateFlow<List<UserDataModel>> =
//        _userProfileState.map { it.distinctBy { user -> user.email } }.stateIn(
//            viewModelScope,
//            SharingStarted.Lazily, emptyList()
//        )

    //    private val _homeScreenState =
//        MutableStateFlow(HomeScreenState.LIVE_USERS_RETRIEVAL_LOADING)
//    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()
//
    init {
        viewModelScope.launch {
            messagesRepo.subscribeToMessages()
        }
    }

//    private fun getCurrentUserEmail() {
//        viewModelScope.launch {
//            messagesRepo.connectSocketToBackend()
//        }
//    }

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
//            Log.i(
//                "MessageViewModel",
//                "data.receiverEmail -> ${data.receiverEmail}  "
//            )
//            _messageState.value += data
        }

//        viewModelScope.launch {
//            collectMessages(userEmail = userEmail, userId = userId)
//        }
    }

//    suspend fun getUsers() {
//        _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_LOADING
//        try {
//
//            messagesRepo.getUsers().collect { users ->
//                Log.i("MessageViewModel", "Collecting users from Viewmodel: $users")
//                _usersState.value = users
//                if (_usersState.value.isEmpty()) {
//                    _homeScreenState.value = HomeScreenState.LIVE_USERS_EMPTY
//                } else {
//                    _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_SUCCESS
//                }
//            }
//        } catch (e: Exception) {
//            Log.e("MessageViewModel", e.toString())
//            _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_ERROR
//        }
//    }

//    suspend fun getUserProfile() {
//        viewModelScope.launch {
//            if (_homeScreenState.value == HomeScreenState.LIVE_USERS_RETRIEVAL_SUCCESS) {
//                _homeScreenState.value = HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_LOADING
//                _usersState.collect { liveUsers ->
//                    messagesRepo.getUsersProfile(liveUsers).collect { liveUserProfiles ->
//                        _userProfileState.value = liveUserProfiles
//                        Log.i(
//                            "MessageViewModel",
//                            "Collecting live user profile from Viewmodel: $liveUserProfiles"
//                        )
//                        if (_userProfileState.value.isNotEmpty()) {
//                            _homeScreenState.value =
//                                HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_SUCCESS
//                        } else {
//                            _homeScreenState.value =
//                                HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_ERROR
//                        }
//                    }
//                }
//            }
//        }
//    }

//    suspend fun getSpecificUserProfileWithEmail(email: String): UserDataModel? {
//        return messagesRepo.getSpecificUserProfileWithEmail(email)
//    }

    fun disconnectUserFromSocket() {
        messagesRepo.disconnectUserFromSocket()
    }

}