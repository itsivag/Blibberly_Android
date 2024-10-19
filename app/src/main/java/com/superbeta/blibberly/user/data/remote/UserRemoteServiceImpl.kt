package com.superbeta.blibberly.user.data.remote

import android.util.Log
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class UserRemoteServiceImpl(private val supabase: SupabaseClient) : UserRemoteService {
    override suspend fun setUser(userDataModel: UserDataModel) {
        try {
            supabase.from("Users").insert(userDataModel)
            Log.i("Database Upload Successful", "")
        } catch (e: Exception) {
            Log.e("Database Upload Error", e.toString())
        }
    }

}