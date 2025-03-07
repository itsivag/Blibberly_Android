package com.superbeta.blibberly_home.domain

import android.util.Log
import com.superbeta.blibberly_chat.data.remote.socket.SocketHandler
import com.superbeta.blibberly_chat.data.remote.supabase.ChatRemoteService
import com.superbeta.blibberly_home.report.data.ReportRemoteService
import com.superbeta.blibberly_models.UserDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeRepoImpl(
    private val socketHandler: SocketHandler,
    private val chatRemoteService: ChatRemoteService,
) : HomeRepo {

    private val _liveUserProfilesState =
        MutableStateFlow<List<UserDataModel>>(emptyList())

    override fun getUsers(): StateFlow<List<String>> {
        return socketHandler.getUsers()
    }

    override suspend fun getUsersProfile(liveUsers: List<String>): StateFlow<List<UserDataModel>> {
        Log.i("HomeRepoImpl", "Live user raw list $liveUsers")
        try {
            val appendProfiles: (UserDataModel) -> Unit =
                { newProfiles ->
                    _liveUserProfilesState.value += newProfiles
                }
            chatRemoteService.getUsersProfile(liveUsers, appendProfiles)
            Log.i("HomeRepoImpl", "Live user " + _liveUserProfilesState.value.toString())
        } catch (e: Exception) {
            Log.e("HomeRepoImpl", "Error getting live user profile : $e")
        }

        return _liveUserProfilesState.asStateFlow()
    }

    override fun getSpecificUserProfileWithEmail(email: String): UserDataModel? {
        return _liveUserProfilesState.value.firstOrNull { profile -> profile.email == email }
    }

}