package com.blibberly.blibberly_likes.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel
import com.blibberly.blibberly_likes.domain.ProfileOpsRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileOpsViewModel(private val profileOpsRepo: ProfileOpsRepo) : ViewModel() {
    private val _profileOpsState = MutableStateFlow<ProfileOpsDataModel?>(null)
    val profileOpsState: StateFlow<ProfileOpsDataModel?> = _profileOpsState.asStateFlow()

    private val _matchedProfilesState = MutableStateFlow<List<ProfileOpsDataModel>>(emptyList())
    val matchedProfilesState: StateFlow<List<ProfileOpsDataModel>> =
        _matchedProfilesState.asStateFlow()

    private val _likedProfilesState = MutableStateFlow<List<ProfileOpsDataModel>>(emptyList())
    val likedProfilesState: StateFlow<List<ProfileOpsDataModel>> = _likedProfilesState.asStateFlow()

    fun getProfileOps(userId: String) {
        viewModelScope.launch {
            _profileOpsState.value = profileOpsRepo.getProfileOps(userId).value
        }
    }

    fun setProfileOps(profileOps: ProfileOpsDataModel) {
        viewModelScope.launch {
            profileOpsRepo.setProfileOps(profileOps)
        }
    }

    fun getMatchedProfiles() {
        viewModelScope.launch {
            _matchedProfilesState.value = profileOpsRepo.getMatchedProfiles().value
            Log.i("Matched Profiles : ", _matchedProfilesState.value.toString())
        }
    }

    fun getLikedProfiles() {
        viewModelScope.launch {
            _likedProfilesState.value = profileOpsRepo.getLikedProfiles().value
            Log.i("Liked Profiles : ", _likedProfilesState.value.toString())
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
//
//                val profileOpsRepoImpl = ProfileOpsRepoImpl(db.ProfileOpsDao())
//                return ProfileOpsViewModel(profileOpsRepoImpl) as T
//            }
//        }
//    }
}