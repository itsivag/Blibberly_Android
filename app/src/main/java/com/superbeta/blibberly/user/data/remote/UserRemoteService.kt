package com.superbeta.blibberly.user.data.remote

import com.superbeta.blibberly.user.data.model.UserDataModel


interface UserRemoteService {
    suspend fun setUser(userDataModel: UserDataModel)
    suspend fun getUser(email: String): UserDataModel?
}