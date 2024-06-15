package com.superbeta.blibberly.auth

import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.utils.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email

class AuthRepositoryImpl : AuthRepository {
    override suspend fun createUser(mEmail: String, mPassword: String) {
        val user = supabase.auth.signUpWith(Email) {
            email = mEmail
            password = mPassword
        }
    }

    override suspend fun loginUser(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun forgotPassword() {
        TODO("Not yet implemented")
    }
}