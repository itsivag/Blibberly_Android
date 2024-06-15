package com.superbeta.blibberly.auth

import android.content.Context
import androidx.credentials.CredentialManager
import com.superbeta.blibberly.user.data.model.UserDataModel
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.CoroutineScope

interface AuthRepository {
    suspend fun createUser(mEmail: String, mPassword: String)
    suspend fun signInWithEmail(mEmail: String, mPassword: String)
    suspend fun signInWithGoogle(
        credentialManager: CredentialManager,
        coroutineScope: CoroutineScope,
        context: Context,
        onSignInSuccess: () -> Unit,
        onUserNotRegistered: () -> Unit

    )

    suspend fun getUserData(): UserInfo
    suspend fun findIfUserRegistered(): UserDataModel?
    suspend fun forgotPassword()
}