package com.blibberly.profile_ops.domain

import android.util.Log
import com.blibberly.profile_ops.data.local.ProfileOpsDao
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.blibberly.profile_ops.data.remote.ProfileOpsRemoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProfileOpsRepoImpl(
    private val db: ProfileOpsDao,
    private val profileOpsRemoteService: ProfileOpsRemoteService
) : ProfileOpsRepo {
    private val _profileOpsState = MutableStateFlow<ProfileOpsDataModel?>(null)

    override suspend fun getProfileOps(userEmail: String): StateFlow<ProfileOpsDataModel?> {
        try {
            val remoteProfileOps = profileOpsRemoteService.getProfileOps(userEmail)
            val localProfileOps = db.getProfileOps(userEmail)
            if (remoteProfileOps != localProfileOps) {
                db.setProfileOps(remoteProfileOps)
                _profileOpsState.value = remoteProfileOps
            } else {
                _profileOpsState.value = localProfileOps
            }
        } catch (e: Exception) {
            Log.i("ProfileOpsRepoImpl", "Error getting profile ops: $e")
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
}