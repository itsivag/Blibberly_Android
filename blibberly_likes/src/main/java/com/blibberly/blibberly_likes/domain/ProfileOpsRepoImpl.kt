package com.blibberly.blibberly_likes.domain

import com.blibberly.blibberly_likes.data.local.ProfileOpsDao
import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileOpsRepoImpl(private val db: ProfileOpsDao) : ProfileOpsRepo {
    private val _profileOpsState = MutableStateFlow<ProfileOpsDataModel?>(null)

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
}