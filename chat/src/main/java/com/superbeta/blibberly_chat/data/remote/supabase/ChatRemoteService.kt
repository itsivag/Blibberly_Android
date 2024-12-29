package com.superbeta.blibberly_chat.data.remote.supabase

import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.model.MessageDataModel

interface ChatRemoteService {
    suspend fun getUsersProfile(
        liveUsers: List<String>,
        appendProfiles: (UserDataModel) -> Unit
    )

    suspend fun saveMessageToRemoteDb(data: MessageDataModel) {}
}