package com.superbeta.blibberly_home.domain

import com.superbeta.blibberly_models.UserDataModel
import kotlinx.coroutines.flow.StateFlow

interface HomeRepo {
    fun getUsers(): StateFlow<List<String>>
    suspend fun getUsersProfile(liveUsers: List<String>): StateFlow<List<com.superbeta.blibberly_models.UserDataModel>>
    fun getSpecificUserProfileWithEmail(email: String): com.superbeta.blibberly_models.UserDataModel?

}