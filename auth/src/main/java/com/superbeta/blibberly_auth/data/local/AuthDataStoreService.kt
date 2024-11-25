package com.superbeta.blibberly_auth.data.local

import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthDataStoreService {
    suspend fun setUserData(user: UserInfo)
    suspend fun getUserData(): Flow<String?>
}