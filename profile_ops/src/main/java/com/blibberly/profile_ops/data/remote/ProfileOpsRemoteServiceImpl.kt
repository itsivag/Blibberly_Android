package com.blibberly.profile_ops.data.remote

import android.util.Log
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ProfileOpsRemoteServiceImpl(supabase: SupabaseClient) : ProfileOpsRemoteService {
    private val supabaseProfileOpsDb = supabase.from("ProfileOps")

    override suspend fun getProfileOps(userEmail: String): ProfileOpsDataModel {
        return supabaseProfileOpsDb.select { filter { ProfileOpsDataModel::userEmail eq userEmail } }
            .decodeSingle<ProfileOpsDataModel>()
    }

    override suspend fun setProfileMetadata(profileOpsDataModel: ProfileOpsDataModel) {
        Log.i("ProfileOpsRemoteServiceImpl", "Setting profile metadata $profileOpsDataModel")
        supabaseProfileOpsDb.upsert(profileOpsDataModel)
    }

}