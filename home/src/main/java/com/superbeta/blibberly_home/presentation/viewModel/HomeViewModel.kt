package com.superbeta.blibberly_home.presentation.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.blibberly_home.domain.HomeRepo
import com.superbeta.blibberly_home.presentation.HomeScreenState
import com.superbeta.blibberly_models.UserDataModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class HomeViewModel(private val homeRepo: HomeRepo) : ViewModel() {
    private val _usersState = MutableStateFlow<List<String>>(emptyList())
    val usersState: StateFlow<List<String>> = _usersState.asStateFlow()

    private val _userProfileState = MutableStateFlow<List<com.superbeta.blibberly_models.UserDataModel>>(emptyList())
    val userProfileState: StateFlow<List<com.superbeta.blibberly_models.UserDataModel>> =
        _userProfileState.map { it.distinctBy { user -> user.email } }.stateIn(
            viewModelScope,
            SharingStarted.Lazily, emptyList()
        )

    private val _homeScreenState =
        MutableStateFlow(HomeScreenState.LIVE_USERS_RETRIEVAL_LOADING)
    val homeScreenState: StateFlow<HomeScreenState> = _homeScreenState.asStateFlow()

//    init {
//        viewModelScope.launch(IO) {
////            messagesRepo.subscribeToMessages()
//            getUsers()
//            getUserProfile()
//        }
//    }

//    private fun getCurrentUserEmail() {
//        viewModelScope.launch(IO) {
//            messagesRepo.connectSocketToBackend()
//        }
//    }

    suspend fun getUsers() {
        viewModelScope.launch(IO) {
            _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_LOADING
            try {

                homeRepo.getUsers().collect { users ->
                    Log.i("MessageViewModel", "Collecting users from Viewmodel: $users")
                    _usersState.value = users
                    if (_usersState.value.isEmpty()) {
                        _homeScreenState.value = HomeScreenState.LIVE_USERS_EMPTY
                    } else {
                        _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_SUCCESS
                    }
                }
            } catch (e: Exception) {
                Log.e("MessageViewModel", e.toString())
                _homeScreenState.value = HomeScreenState.LIVE_USERS_RETRIEVAL_ERROR
            }
        }
    }

    suspend fun getUserProfile() {
        viewModelScope.launch(IO) {
            if (_homeScreenState.value == HomeScreenState.LIVE_USERS_RETRIEVAL_SUCCESS) {
                _homeScreenState.value = HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_LOADING
                _usersState.collect { liveUsers ->
                    homeRepo.getUsersProfile(liveUsers).collect { liveUserProfiles ->
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

//    fun getSpecificUserProfileWithEmail(email: String): UserDataModel? {
//        return messagesRepo.getSpecificUserProfileWithEmail(email)
//    }

//    fun disconnectUserFromSocket() {
//        messagesRepo.disconnectUserFromSocket()
//    }

}