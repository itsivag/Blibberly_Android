package com.blibberly.profile_ops.data.remote

import com.blibberly.profile_ops.data.model.ProfileOpsDataModel

interface ProfileOpsRemoteService {
    suspend fun getProfileOps(userId: String): ProfileOpsDataModel
    suspend fun setProfileOps(profileOps: ProfileOpsDataModel)
    suspend fun getMatchedProfiles(): List<ProfileOpsDataModel>
    suspend fun getLikedProfiles(): List<ProfileOpsDataModel>

}