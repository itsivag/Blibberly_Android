package com.superbeta.blibberly_chat.data.remote.supabase

import com.google.firebase.auth.FirebaseUser
import com.superbeta.blibberly_models.UserDataModel
import com.superbeta.blibberly_chat.data.model.MessageDataModel

interface ChatRemoteService {
    suspend fun retrieveSession(): FirebaseUser?
    suspend fun getUsersProfile(
        liveUsers: List<String>,
        appendProfiles: (UserDataModel) -> Unit
    )

    suspend fun saveMessageToRemoteDb(data: MessageDataModel) {}
    suspend fun getMessagesFromRemoteDb(
        currUserEmail: String,
        receiverEmail: String
    ): List<MessageDataModel>

    suspend fun deleteMessagesFromRemoteDb(currUserEmail: String, receiverEmail: String)

}