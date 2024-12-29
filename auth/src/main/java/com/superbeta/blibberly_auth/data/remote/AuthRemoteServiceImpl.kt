package com.superbeta.blibberly_auth.data.remote

import android.util.Log
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class AuthRemoteServiceImpl(private val supabase: SupabaseClient) : AuthRemoteService {
    override suspend fun signInWithGoogle(googleIdToken: String, rawNonce: String) {
        supabase.auth.signInWith(IDToken) {
            idToken = googleIdToken
            provider = Google
            nonce = rawNonce
        }
    }

    override suspend fun retrieveSession(): UserInfo {
        return supabase.auth.retrieveUserForCurrentSession(updateSession = true)
    }

    override suspend fun findIfUserRegistered(email: String): Boolean {
        val userData =
            supabase.from("Users").select { filter { UserDataModel::email eq email } }
                .decodeSingleOrNull<UserDataModel>()
        Log.i("AuthRemoteServiceImpl", "User Data From Supabase" + userData.toString())

        return userData != null
    }
}