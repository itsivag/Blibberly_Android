package com.blibberly.blibberly_likes.data.remote

import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel

interface ProfileOpsRemoteService {
    suspend fun getProfileOps(userId: String): ProfileOpsDataModel
    suspend fun setProfileOps(profileOps: ProfileOpsDataModel)
    suspend fun getMatchedProfiles(): List<ProfileOpsDataModel>
    suspend fun getLikedProfiles(): List<ProfileOpsDataModel>

}