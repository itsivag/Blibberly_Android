package com.superbeta.blibberly.user.data.remote

import android.util.Log
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class UserRemoteServiceImpl(private val supabase: SupabaseClient) : UserRemoteService {
    override suspend fun setUser(userDataModel: UserDataModel) {
        try {
            supabase.from("Users").insert(userDataModel)
            Log.i("Database Upload Successful", userDataModel.toString())
        } catch (e: Exception) {
            Log.e("Database Upload Error", e.toString())
        }
    }

    override suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData, email: String) {
        try {
            supabase.from("Users")
                .update({ set("photoMetaData", photoMetaData) }) {
                    filter {
                        UserDataModel::email eq email
                    }
                }
            Log.i("Database Update Successful", photoMetaData.toString())
        } catch (e: Exception) {
            Log.e("Database Update Error", e.toString())
        }
    }

    override suspend fun updateUserName(name: String, email: String) {
        try {
            supabase.from("Users")
                .update({ set("name", name) }) {
                    filter {
                        UserDataModel::email eq email
                    }
                }
            Log.i("Database Update Successful", name)
        } catch (e: Exception) {
            Log.e("Database Update Error", e.toString())
        }
    }

    override suspend fun updateAge(age: Int, email: String) {
        try {
            supabase.from("Users")
                .update({ set("age", age) }) {
                    filter {
                        UserDataModel::email eq email
                    }
                }
            Log.i("Database Update Successful", age.toString())
        } catch (e: Exception) {
            Log.e("Database Update Error", e.toString())
        }
    }

    override suspend fun updateInterests(interests: List<String>, email: String) {
        try {
            supabase.from("Users")
                .update({ set("interests", interests) }) {
                    filter {
                        UserDataModel::email eq email
                    }
                }
            Log.i("Database Update Successful", interests.toString())
        } catch (e: Exception) {
            Log.e("Database Update Error", e.toString())
        }
    }

    override suspend fun updateWeight(weight: Double, email: String) {
        try {
            supabase.from("Users")
                .update({ set("weight", weight) }) {
                    filter {
                        UserDataModel::email eq email
                    }
                }
            Log.i("Database Update Successful", weight.toString())
        } catch (e: Exception) {
            Log.e("Database Update Error", e.toString())
        }
    }

    override suspend fun updateAboutMe(aboutMe: String, email: String) {
        try {
            supabase.from("Users")
                .update({ set("aboutMe", aboutMe) }) {
                    filter {
                        UserDataModel::email eq email
                    }
                }
            Log.i("Database Update Successful", aboutMe)
        } catch (e: Exception) {
            Log.e("Database Update Error", e.toString())
        }
    }

    override suspend fun updateHeight(height: Double, email: String) {
        try {
            supabase.from("Users")
                .update({ set("height", height) }) {
                    filter {
                        UserDataModel::email eq email
                    }
                }
            Log.i("Database Update Successful", height.toString())
        } catch (e: Exception) {
            Log.e("Database Update Error", e.toString())
        }
    }

    override suspend fun getUser(email: String): UserDataModel? {
        try {
            val userData =
                supabase.from("Users").select { filter { UserDataModel::email eq email } }
                    .decodeSingle<UserDataModel>()
            Log.i("UserData From Database", userData.toString())

            return userData
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

}