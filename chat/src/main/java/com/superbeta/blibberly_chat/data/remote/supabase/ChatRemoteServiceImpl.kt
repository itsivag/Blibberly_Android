package com.superbeta.blibberly_chat.data.remote.supabase

import android.util.Log
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.model.MessageDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ChatRemoteServiceImpl(supabase: SupabaseClient) : ChatRemoteService {
    private val supabaseUsersDb = supabase.from("Users")
    private val supabaseMessageDb = supabase.from("Messages")

    override suspend fun getUsersProfile(
        liveUsers: List<String>, appendProfiles: (UserDataModel) -> Unit
    ) {
        Log.i("ChatRemoteServiceImpl", "Live user list: $liveUsers")

        try {
            for (user in liveUsers) {
                Log.i("ChatRemoteServiceImpl", "User :  $user")
                val userProfile = supabaseUsersDb.select {
                    filter {
                        UserDataModel::email eq user
                    }
                }.decodeSingle<UserDataModel>()
//            _liveUserProfilesState.value += userProfile
                appendProfiles(userProfile)
            }
        } catch (e: Exception) {
            Log.e("ChatRemoteServiceImpl", "Error getting user profile: $e")
        }
    }

    override suspend fun saveMessageToRemoteDb(data: MessageDataModel) {
        Log.i("ChatRemoteServiceImpl", "Saving Message To Db : $data")
        try {
            supabaseMessageDb.upsert(data)
        } catch (e: Exception) {
            Log.e("ChatRemoteServiceImpl", "Error saving message to remote db: $e")
        }
    }

    override suspend fun getMessagesFromRemoteDb(
        currUserEmail: String, receiverEmail: String
    ): List<MessageDataModel> {
        return supabaseMessageDb.select {
            filter {
                and {
                    eq("senderEmail", receiverEmail)
                    eq("receiverEmail", currUserEmail)
                }
            }
        }.decodeList<MessageDataModel>()
    }

    override suspend fun deleteMessagesFromRemoteDb(currUserEmail: String, receiverEmail: String) {
        try {
            supabaseMessageDb.delete {
                filter {
                    and {
                        eq("senderEmail", receiverEmail)
                        eq("receiverEmail", currUserEmail)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("ChatRemoteServiceImpl", "Error deleting messages from remote db: $e")
        }
    }
}