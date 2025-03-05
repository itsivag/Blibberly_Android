package com.superbeta.profile_ops.data.remote

import android.util.Log
import com.blibberly.profile_ops.data.model.ProfileOp
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.superbeta.blibberly_models.UserDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ProfileOpsRemoteServiceImpl(supabase: SupabaseClient) : ProfileOpsRemoteService {
    private val supabaseProfileOpsDb = supabase.from("ProfileOps")
    private val supabaseUsersDb = supabase.from("Users")

    override suspend fun getProfileOps(userEmail: String): ProfileOpsDataModel {
        return supabaseProfileOpsDb.select { filter { eq("userEmail", userEmail) } }
            .decodeSingle<ProfileOpsDataModel>()
    }

    override suspend fun setProfileMetadata(profileOpsDataModel: ProfileOpsDataModel) {
        Log.i("ProfileOpsRemoteServiceImpl", "Setting profile metadata $profileOpsDataModel")
        supabaseProfileOpsDb.upsert(profileOpsDataModel)
    }

    override suspend fun getLikedUserProfiles(
        likedUserEmails: List<ProfileOp>,
        appendProfiles: (com.superbeta.blibberly_models.UserDataModel) -> Unit
    ) {

        Log.i("ProfileOpsRemoteServiceImpl", "liked users list: $likedUserEmails")

        try {
            for (user in likedUserEmails) {
                Log.i("ProfileOpsRemoteServiceImpl", "User :  $user")
                val userProfile = supabaseUsersDb.select {
                    filter {
                        com.superbeta.blibberly_models.UserDataModel::email eq user.userEmail
                    }
                }.decodeSingle<com.superbeta.blibberly_models.UserDataModel>()
//            _liveUserProfilesState.value += userProfile
                appendProfiles(userProfile)
            }
        } catch (e: Exception) {
            Log.e("ProfileOpsRemoteServiceImpl", "Error getting user profile: $e")
        }


    }

    override suspend fun getMatchedUserProfiles(
        matchedUserEmails: List<ProfileOp>,
        appendProfiles: (com.superbeta.blibberly_models.UserDataModel) -> Unit
    ) {
        Log.i("ProfileOpsRemoteServiceImpl", "matched users list: $matchedUserEmails")

        try {
            for (user in matchedUserEmails) {
                Log.i("ProfileOpsRemoteServiceImpl", "User :  $user")
                val userProfile = supabaseUsersDb.select {
                    filter {
                        com.superbeta.blibberly_models.UserDataModel::email eq user.userEmail
                    }
                }.decodeSingle<com.superbeta.blibberly_models.UserDataModel>()
                appendProfiles(userProfile)
            }
        } catch (e: Exception) {
            Log.e("ProfileOpsRemoteServiceImpl", "Error getting user profile: $e")
        }

    }

}