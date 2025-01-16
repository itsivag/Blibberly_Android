package com.superbeta.blibberly_auth.domain

import com.superbeta.blibberly_auth.utils.AuthState
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
//    suspend fun updateAuthState()
    fun getAuthState(): StateFlow<AuthState>
    suspend fun createUser(mEmail: String, mPassword: String)
    suspend fun signInWithEmail(mEmail: String, mPassword: String)
    suspend fun signInWithGoogle()

    suspend fun getUserData(): UserInfo
    suspend fun findIfUserRegistered(email: String): Boolean
    suspend fun forgotPassword()

    suspend fun getUsersFromDataStore(): Flow<String?>
    suspend fun logOut()
    suspend fun signUpWithEmail(email: String, password: String)

}