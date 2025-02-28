package com.superbeta.blibberly.user.data.remote

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from

class UserRemoteServiceImpl(private val supabase: SupabaseClient) : UserRemoteService {
    private val usersTable = supabase.from("Users")
    private val deletedUsersTable = supabase.from("DeletedUsers")

    override suspend fun setUser(userDataModel: UserDataModel) {
        try {
            usersTable.insert(userDataModel)
            Log.i("Database Upload Successful", userDataModel.toString())
        } catch (e: Exception) {
            Log.e("Database Upload Error", e.toString())
        }
    }

    override suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData, email: String) {
        try {
            usersTable
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
            usersTable
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
            usersTable
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
            usersTable
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
            usersTable
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
            usersTable
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
            usersTable
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
                usersTable.select { filter { UserDataModel::email eq email } }
                    .decodeSingle<UserDataModel>()
            Log.i("UserData From Database", userData.toString())

            return userData
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("Error getting UserData From Database", e.toString())
            return null
        }
    }

    override suspend fun deleteAccount(email: String) {
        try {
            usersTable.delete { filter { UserDataModel::email eq email } }
//            usersTable.select { filter { UserDataModel::email eq email } }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun getUserEmail(): String? {
        return Firebase.auth.currentUser?.email
    }

//    override suspend fun retrieveSession(): UserInfo? {
//        try {
//            Log.i("UserRemoteServiceImpl", "Retrieving Session")
//            return supabase.auth.retrieveUserForCurrentSession()
//        } catch (e: Exception) {
//            Log.e("UserRemoteServiceImpl", "Error retrieving session: $e")
//            return null
//        }
//    }

}