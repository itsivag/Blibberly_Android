package com.superbeta.blibberly_home.domain

import com.superbeta.blibberly_auth.model.UserDataModel
import kotlinx.coroutines.flow.StateFlow

interface HomeRepo {
    fun getUsers(): StateFlow<List<String>>
    suspend fun getUsersProfile(liveUsers: List<String>): StateFlow<List<UserDataModel>>
    fun getSpecificUserProfileWithEmail(email: String): UserDataModel?

}