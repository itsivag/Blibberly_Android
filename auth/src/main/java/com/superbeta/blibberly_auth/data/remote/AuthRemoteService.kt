package com.superbeta.blibberly_auth.data.remote

import com.superbeta.blibberly_auth.utils.AuthState
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface AuthRemoteService {
//    suspend fun signInWithGoogle(googleIdToken: String, rawNonce: String)
//    suspend fun retrieveSession(): UserInfo
    suspend fun findIfUserRegistered(email: String): Boolean
//    suspend fun signInWithEmail(mEmail: String, mPassword: String)
//    suspend fun signUpWithEmail(mEmail: String, mPassword: String)
//    suspend fun updateAuthStatus()
//    fun getAuthState(): StateFlow<AuthState>
}