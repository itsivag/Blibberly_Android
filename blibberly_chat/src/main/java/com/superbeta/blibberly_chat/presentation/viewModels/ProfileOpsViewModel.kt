package com.superbeta.blibberly_chat.presentation.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel
import com.blibberly.blibberly_likes.domain.ProfileOpsRepo
import com.blibberly.blibberly_likes.domain.ProfileOpsRepoImpl
import com.superbeta.blibberly_chat.data.local.BlibberlyRoomInstanceProvider
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

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val db = BlibberlyRoomInstanceProvider.getMessagesDb(application.applicationContext)

                val profileOpsRepoImpl = ProfileOpsRepoImpl(db.ProfileOpsDao())
                return ProfileOpsViewModel(profileOpsRepoImpl) as T
            }
        }
    }
}