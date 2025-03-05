package com.superbeta.blibberly_auth.data.remote

import android.util.Log
import com.superbeta.blibberly_models.UserDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class AuthRemoteServiceImpl(private val supabase: SupabaseClient) : AuthRemoteService {

    override suspend fun findIfUserRegistered(email: String): Boolean {
        try {
            val userData =
                supabase.from("Users").select { filter { UserDataModel::email eq email } }
                    .decodeSingleOrNull<UserDataModel>()
            Log.i("AuthRemoteServiceImpl", "User Data From Supabase" + userData.toString())
            return userData != null
        } catch (e: Exception) {
            Log.i("AuthRemoteServiceImpl", "Error From Supabase : $e")
            return false
        }
    }
}