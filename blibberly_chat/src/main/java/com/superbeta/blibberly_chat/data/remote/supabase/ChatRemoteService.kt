package com.superbeta.blibberly_chat.data.remote.supabase

import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import kotlinx.coroutines.flow.StateFlow

interface ChatRemoteService {
    suspend fun getUsersProfile(
        liveUsers: List<SocketUserDataModelItem>,
        appendProfiles: (UserDataModel) -> Unit
    )
}