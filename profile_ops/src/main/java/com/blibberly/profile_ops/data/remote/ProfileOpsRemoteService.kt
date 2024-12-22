package com.blibberly.profile_ops.data.remote

import com.blibberly.profile_ops.data.model.ProfileOp
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.blibberly.profile_ops.data.model.UserDataModel
import kotlinx.coroutines.flow.StateFlow


interface ProfileOpsRemoteService {
//    suspend fun getProfileOps(userEmail: String): ProfileOpsDataModel
//    suspend fun setProfileOps(profileOps: ProfileOpsDataModel)
//    suspend fun getMatchedProfiles(): List<ProfileOpsDataModel>
//    suspend fun getLikedProfiles(): List<ProfileOpsDataModel>

    suspend fun getProfileOps(userEmail: String): ProfileOpsDataModel
    suspend fun setProfileMetadata(profileOpsDataModel: ProfileOpsDataModel)
    suspend fun getLikedUserProfiles(
        likedUserEmails: List<ProfileOp>,
        appendProfiles: (UserDataModel) -> Unit
    )


}