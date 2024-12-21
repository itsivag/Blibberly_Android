package com.blibberly.profile_ops.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.blibberly.profile_ops.domain.ProfileOpsRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileOpsViewModel(private val profileOpsRepo: ProfileOpsRepo) : ViewModel() {
    private val _profileOpsState = MutableStateFlow<ProfileOpsDataModel?>(null)
    val profileOpsState: StateFlow<ProfileOpsDataModel?> = _profileOpsState.asStateFlow()

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
}