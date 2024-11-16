package com.superbeta.blibberly_chat.data.remote.supabase

import android.util.Log
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ChatRemoteServiceImpl(supabase: SupabaseClient) : ChatRemoteService {
    private val supabaseUsersDb = supabase.from("Users")

    override suspend fun getUsersProfile(
        liveUsers: List<SocketUserDataModelItem>,
        appendProfiles: (UserDataModel) -> Unit
    ) {
        Log.i("ChatRemoteServiceImpl", "Live user list: $liveUsers")

        try {
            for (user in liveUsers) {
                Log.i("ChatRemoteServiceImpl", "User :  $user")
                val userProfile = supabaseUsersDb.select {
                    filter {
                        UserDataModel::email eq user.email
                    }
                }.decodeSingle<UserDataModel>()
//            _liveUserProfilesState.value += userProfile
                appendProfiles(userProfile)
            }
        } catch (e: Exception) {
            Log.e("ChatRemoteServiceImpl", "Error getting user profile: $e")
        }
    }

}