package com.superbeta.blibberly_home.domain

import android.util.Log
import com.superbeta.blibberly_chat.data.remote.socket.SocketHandler
import com.superbeta.blibberly_chat.data.remote.supabase.ChatRemoteService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeRepoImpl(
    private val socketHandler: SocketHandler,
    private val chatRemoteService: ChatRemoteService,
) : HomeRepo {

//    private val _messageState = MutableStateFlow<List<MessageDataModel>>(arrayListOf())
    private val _liveUserProfilesState =
        MutableStateFlow<List<com.superbeta.blibberly_auth.user.data.model.UserDataModel>>(emptyList())

    override fun getUsers(): StateFlow<List<String>> {
        return socketHandler.getUsers()
    }

    override suspend fun getUsersProfile(liveUsers: List<String>): StateFlow<List<com.superbeta.blibberly_auth.user.data.model.UserDataModel>> {
        Log.i("live user raw list", liveUsers.toString())
        try {
//            for (email in liveUsers) {
//                val userProfile = supabaseUsersDb.select {
//                    filter {
//                        UserDataModel::email eq email.username
//                    }
//                }.decodeSingle<UserDataModel>()
//                _liveUserProfilesState.value += userProfile
//            }
            val appendProfiles: (com.superbeta.blibberly_auth.user.data.model.UserDataModel) -> Unit =
                { newProfiles ->
                    _liveUserProfilesState.value += newProfiles
                }
            chatRemoteService.getUsersProfile(liveUsers, appendProfiles)
            Log.i("live user", _liveUserProfilesState.value.toString())
        } catch (e: Exception) {
            Log.e("Error getting live user profile", e.toString())
        }

        return _liveUserProfilesState.asStateFlow()
    }

    override fun getSpecificUserProfileWithEmail(email: String): com.superbeta.blibberly_auth.user.data.model.UserDataModel? {
        return _liveUserProfilesState.value.firstOrNull { profile -> profile.email == email }
    }

}