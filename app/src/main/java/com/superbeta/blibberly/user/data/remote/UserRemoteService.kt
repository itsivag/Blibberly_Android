package com.superbeta.blibberly.user.data.remote

import com.superbeta.blibberly_auth.user.data.model.UserDataModel

interface UserRemoteService {
    suspend fun setUser(userDataModel: UserDataModel)
}