package com.blibberly.profile_ops.data.remote

import android.util.Log
import com.blibberly.profile_ops.data.model.ProfileOp
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.blibberly.profile_ops.data.model.UserDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.StateFlow

class ProfileOpsRemoteServiceImpl(supabase: SupabaseClient) : ProfileOpsRemoteService {
    private val supabaseProfileOpsDb = supabase.from("ProfileOps")
    private val supabaseUsersDb = supabase.from("Users")

    override suspend fun getProfileOps(userEmail: String): ProfileOpsDataModel {
        return supabaseProfileOpsDb.select { filter { "userEmail" to userEmail } }
            .decodeSingle<ProfileOpsDataModel>()
    }

    override suspend fun setProfileMetadata(profileOpsDataModel: ProfileOpsDataModel) {
        Log.i("ProfileOpsRemoteServiceImpl", "Setting profile metadata $profileOpsDataModel")
        supabaseProfileOpsDb.upsert(profileOpsDataModel)
    }

    override suspend fun getLikedUserProfiles(
        likedUserEmails: List<ProfileOp>,
        appendProfiles: (UserDataModel) -> Unit
    ) {

        Log.i("ProfileOpsRemoteServiceImpl", "liked users list: $likedUserEmails")

        try {
            for (user in likedUserEmails) {
                Log.i("ProfileOpsRemoteServiceImpl", "User :  $user")
                val userProfile = supabaseUsersDb.select {
                    filter {
                        UserDataModel::email eq user.userEmail
                    }
                }.decodeSingle<UserDataModel>()
//            _liveUserProfilesState.value += userProfile
                appendProfiles(userProfile)
            }
        } catch (e: Exception) {
            Log.e("ProfileOpsRemoteServiceImpl", "Error getting user profile: $e")
        }


    }

}