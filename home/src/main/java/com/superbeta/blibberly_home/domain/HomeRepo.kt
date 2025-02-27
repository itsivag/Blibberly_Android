package com.superbeta.blibberly_home.domain

import kotlinx.coroutines.flow.StateFlow

interface HomeRepo {
    fun getUsers(): StateFlow<List<String>>
    suspend fun getUsersProfile(liveUsers: List<String>): StateFlow<List<com.superbeta.blibberly_auth.user.data.model.UserDataModel>>
    fun getSpecificUserProfileWithEmail(email: String): com.superbeta.blibberly_auth.user.data.model.UserDataModel?

}