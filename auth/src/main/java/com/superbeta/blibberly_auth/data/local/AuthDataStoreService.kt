package com.superbeta.blibberly_auth.data.local

import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthDataStoreService {
//    suspend fun setUserData(user: UserProfile)
//    suspend fun getUserData(): Flow<UserProfile?>
//    suspend fun deleteUserData()

    suspend fun setAccessToken(token: String)
    suspend fun getAccessToken(): String?
    suspend fun deleteAccessToken()
}