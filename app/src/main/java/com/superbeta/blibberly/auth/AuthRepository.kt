package com.superbeta.blibberly.auth

import android.app.Activity
import com.superbeta.blibberly.user.data.model.UserDataModel

interface AuthRepository {
    suspend fun createUser(email: String, password: String)
    suspend fun loginUser(email: String, password: String)
    suspend fun forgotPassword()
}