package com.superbeta.blibberly_auth.data.local

import com.auth0.android.result.UserProfile
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthDataStoreService {
//    suspend fun setUserData(user: UserProfile)
//    suspend fun getUserData(): Flow<UserProfile?>
//    suspend fun deleteUserData()

    suspend fun setAccessToken(token: String)
    suspend fun getAccessToken(): Flow<String?>
    suspend fun deleteAccessToken()
}