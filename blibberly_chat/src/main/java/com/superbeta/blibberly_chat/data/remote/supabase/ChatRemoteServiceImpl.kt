package com.superbeta.blibberly_chat.data.remote.supabase

import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.StateFlow

class ChatRemoteServiceImpl(private val supabase: SupabaseClient) : ChatRemoteService {
    private val supabaseUsersDb = supabase.from("Users")

    override suspend fun getUsersProfile(
        liveUsers: List<SocketUserDataModelItem>,
        appendProfiles: (UserDataModel) -> Unit
    ) {
        for (email in liveUsers) {
            val userProfile = supabaseUsersDb.select {
                filter {
                    UserDataModel::email eq email.username
                }
            }.decodeSingle<UserDataModel>()
//            _liveUserProfilesState.value += userProfile
            appendProfiles(userProfile)
        }
    }

}