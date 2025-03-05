package com.superbeta.profile_ops.domain

import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.superbeta.blibberly_models.UserDataModel
import kotlinx.coroutines.flow.StateFlow

interface ProfileOpsRepo {
    suspend fun getProfileOps(userEmail: String): StateFlow<ProfileOpsDataModel?>
    suspend fun setProfileOps(profileOpsDataModel: ProfileOpsDataModel)
    suspend fun getLikedProfiles(): StateFlow<List<com.superbeta.blibberly_models.UserDataModel>>
    suspend fun getMatchedProfiles(): StateFlow<List<com.superbeta.blibberly_models.UserDataModel>>
}