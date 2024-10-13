package com.blibberly.blibberly_likes.domain

import com.blibberly.blibberly_likes.data.local.ProfileOpsDao
import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileOpsRepoImpl(private val db: ProfileOpsDao) : ProfileOpsRepo {
    private val _profileOpsState = MutableStateFlow<ProfileOpsDataModel?>(null)
    private val _matchedProfilesState = MutableStateFlow<List<ProfileOpsDataModel>>(emptyList())
    private val _likedProfilesState = MutableStateFlow<List<ProfileOpsDataModel>>(emptyList())

    override suspend fun getProfileOps(userId: String): StateFlow<ProfileOpsDataModel?> {
        try {
            _profileOpsState.value = db.getProfileOps(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _profileOpsState.asStateFlow()
    }

    override suspend fun setProfileOps(profileOpsDataModel: ProfileOpsDataModel) {
        try {
            db.setProfileOps(profileOpsDataModel)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getMatchedProfiles(): StateFlow<List<ProfileOpsDataModel>> {
        try {
            _matchedProfilesState.value = db.getMatchedProfiles()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return _matchedProfilesState
    }

    override suspend fun getLikedProfiles(): StateFlow<List<ProfileOpsDataModel>> {
        try {
            _likedProfilesState.value = db.getLikedProfiles()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return _likedProfilesState.asStateFlow()
    }
}