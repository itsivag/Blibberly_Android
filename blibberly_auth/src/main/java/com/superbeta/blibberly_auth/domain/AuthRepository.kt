package com.superbeta.blibberly_auth.domain

import com.superbeta.blibberly_auth.utils.AuthState
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface AuthRepository {
    suspend fun getAuthState(): MutableStateFlow<AuthState>
    suspend fun createUser(mEmail: String, mPassword: String)
    suspend fun signInWithEmail(mEmail: String, mPassword: String)
    suspend fun signInWithGoogle()

    suspend fun getUserData(): UserInfo
    suspend fun findIfUserRegistered(): Boolean
    suspend fun forgotPassword()

    suspend fun getUsersFromDataStore(): Flow<String?>
}