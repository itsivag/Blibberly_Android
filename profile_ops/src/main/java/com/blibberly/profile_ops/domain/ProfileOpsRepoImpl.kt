package com.blibberly.profile_ops.domain

import android.util.Log
import com.blibberly.profile_ops.data.local.ProfileOpsDao
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.blibberly.profile_ops.data.model.UserDataModel
import com.blibberly.profile_ops.data.remote.ProfileOpsRemoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileOpsRepoImpl(
    private val db: ProfileOpsDao,
    private val profileOpsRemoteService: ProfileOpsRemoteService,
) : ProfileOpsRepo {
    private val _profileOpsState = MutableStateFlow<ProfileOpsDataModel?>(null)
    private val _likeUserProfileState = MutableStateFlow<List<UserDataModel>>(emptyList())
    private val _matchedUserProfileState = MutableStateFlow<List<UserDataModel>>(emptyList())


    override suspend fun getProfileOps(userEmail: String): StateFlow<ProfileOpsDataModel?> {
        try {
            val remoteProfileOps = profileOpsRemoteService.getProfileOps(userEmail)
//            val localProfileOps = db.getProfileOps(userEmail)
//            if (remoteProfileOps != localProfileOps) {
            db.setProfileOps(remoteProfileOps)
            _profileOpsState.value = remoteProfileOps
//            } else {
//                _profileOpsState.value = localProfileOps
//            }
        } catch (e: Exception) {
            Log.e("ProfileOpsRepoImpl", "Error getting profile ops: $e")
        }

        return _profileOpsState.asStateFlow()
    }

    override suspend fun setProfileOps(profileOpsDataModel: ProfileOpsDataModel) {
        try {
            db.setProfileOps(profileOpsDataModel)
            profileOpsRemoteService.setProfileMetadata(profileOpsDataModel)
        } catch (e: Exception) {
            Log.i("ProfileOpsRepoImpl", "Error setting profile ops: $e")
        }
    }

    override suspend fun getLikedProfiles(): StateFlow<List<UserDataModel>> {

        try {
            val appendProfiles: (UserDataModel) -> Unit = { newProfiles ->
                _likeUserProfileState.value += newProfiles
            }
            _profileOpsState.value?.let {
                profileOpsRemoteService.getLikedUserProfiles(
                    likedUserEmails = it.likedProfiles,
                    appendProfiles = appendProfiles
                )
            }

            Log.i("ProfileOpsRepoImpl", "Liked User Profiles: ${_likeUserProfileState.value}")
        } catch (e: Exception) {
            Log.i("ProfileOpsRepoImpl", "Error getting liked profiles: $e")
        }
        return _likeUserProfileState.asStateFlow()
    }

    override suspend fun getMatchedProfiles(): StateFlow<List<UserDataModel>> {
        try {

            val appendProfiles: (UserDataModel) -> Unit = { newProfiles ->
                _matchedUserProfileState.value += newProfiles
            }
            _profileOpsState.value?.let {
                profileOpsRemoteService.getMatchedUserProfiles(
                    matchedUserEmails = it.matchedProfiles,
                    appendProfiles = appendProfiles
                )
            }

            Log.i("ProfileOpsRepoImpl", "Matched User Profiles: ${_matchedUserProfileState.value}")
        } catch (e: Exception) {
            Log.i("ProfileOpsRepoImpl", "Error getting Matched profiles: $e")
        }

        return _matchedUserProfileState.asStateFlow()
    }
}