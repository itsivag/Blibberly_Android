package com.blibberly.blibberly_likes.domain

import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel
import kotlinx.coroutines.flow.StateFlow

interface ProfileOpsRepo {
    suspend fun getProfileOps(userId: String): StateFlow<ProfileOpsDataModel?>
    suspend fun setProfileOps(profileOpsDataModel: ProfileOpsDataModel)
}