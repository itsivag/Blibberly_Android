package com.blibberly.profile_ops.domain

import com.blibberly.profile_ops.data.model.ProfileOp
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.blibberly.profile_ops.data.model.UserDataModel
import kotlinx.coroutines.flow.StateFlow

interface ProfileOpsRepo {
    suspend fun getProfileOps(userEmail: String): StateFlow<ProfileOpsDataModel?>
    suspend fun setProfileOps(profileOpsDataModel: ProfileOpsDataModel)
    suspend fun getLikedProfiles(): StateFlow<List<UserDataModel>>
    suspend fun getMatchedProfiles(): StateFlow<List<UserDataModel>>
}