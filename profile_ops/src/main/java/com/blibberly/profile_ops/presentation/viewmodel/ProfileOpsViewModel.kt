package com.blibberly.profile_ops.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.blibberly.profile_ops.data.model.UserDataModel
import com.blibberly.profile_ops.domain.ProfileOpsRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileOpsViewModel(
    private val profileOpsRepo: ProfileOpsRepo,
) : ViewModel() {
    private val _profileOpsState = MutableStateFlow<ProfileOpsDataModel?>(null)
    val profileOpsState: StateFlow<ProfileOpsDataModel?> = _profileOpsState.asStateFlow()

    private val _likedUserProfileState = MutableStateFlow<List<UserDataModel>>(emptyList())
    val likedUserProfileState: StateFlow<List<UserDataModel>> =
        _likedUserProfileState.map { it.distinctBy { user -> user.email } }.stateIn(
            viewModelScope,
            SharingStarted.Lazily, emptyList()
        )

    private val _matchedUserProfileState = MutableStateFlow<List<UserDataModel>>(emptyList())
    val matchedUserProfileState: StateFlow<List<UserDataModel>> =
        _matchedUserProfileState.map { it.distinctBy { user -> user.email } }.stateIn(
            viewModelScope,
            SharingStarted.Lazily, emptyList()
        )

    fun getProfileOps(currUserEmail: String) {
        viewModelScope.launch(IO) {
            val profileOps = profileOpsRepo.getProfileOps(currUserEmail).value
            if (profileOps == null) {
                _profileOpsState.value = ProfileOpsDataModel(
                    userEmail = currUserEmail,
                    likedProfiles = emptyList(),
                    dislikedProfiles = emptyList(),
                    matchedProfiles = emptyList(),
                    unMatchedProfiles = emptyList(),
                    reportedProfiles = emptyList(),
                )
            } else {
                _profileOpsState.value = profileOps
            }
            Log.i("ProfileOpsViewModel", "Profile Ops => " + _profileOpsState.value)
        }
    }

    fun setProfileOps(profileOps: ProfileOpsDataModel) {
        viewModelScope.launch(IO) {
            Log.i("ProfileOpsViewModel", "Setting Profile Ops")
            profileOpsRepo.setProfileOps(profileOps)
        }
    }


    fun getLikedUserProfiles() {
        viewModelScope.launch(IO) {
            _likedUserProfileState.value = profileOpsRepo.getLikedProfiles().value
            Log.i("ProfileOpsViewModel", "Get Liked Profiles : ${_likedUserProfileState.value}")
        }
    }

    fun getMatchedUserProfiles(){
        viewModelScope.launch(IO) {
            _matchedUserProfileState.value = profileOpsRepo.getMatchedProfiles().value
            Log.i("ProfileOpsViewModel", "Get Matched Profiles : ${_matchedUserProfileState.value}")
        }

    }
}