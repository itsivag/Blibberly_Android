package com.superbeta.blibberly.user.data.remote

import com.superbeta.blibberly_models.PhotoMetaData
import com.superbeta.blibberly_models.UserDataModel


interface UserRemoteService {
    suspend fun setUser(userDataModel: com.superbeta.blibberly_models.UserDataModel)
    suspend fun updatePhotoMetaData(photoMetaData: com.superbeta.blibberly_models.PhotoMetaData, email: String)
    suspend fun updateUserName(name: String, email: String)
    suspend fun updateDob(dob: String, email: String)
    suspend fun updateInterests(interests: List<String>, email: String)
    suspend fun updateWeight(weight: Double, email: String)
    suspend fun updateAboutMe(aboutMe: String, email: String)
    suspend fun updateHeight(height: Double, email: String)
    suspend fun getUser(email: String): com.superbeta.blibberly_models.UserDataModel?
    suspend fun deleteAccount(email: String)
    suspend fun getUserEmail(): String?
}