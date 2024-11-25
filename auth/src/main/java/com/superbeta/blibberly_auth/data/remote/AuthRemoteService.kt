package com.superbeta.blibberly_auth.data.remote

import io.github.jan.supabase.gotrue.user.UserInfo

interface AuthRemoteService {
    suspend fun signInWithGoogle(googleIdToken: String, rawNonce: String)
    suspend fun retrieveSession(): UserInfo
    suspend fun findIfUserRegistered(): Boolean
}