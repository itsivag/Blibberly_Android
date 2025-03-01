package com.superbeta.blibberly.user.data.remote

import com.superbeta.blibberly_auth.model.PhotoMetaData
import com.superbeta.blibberly_auth.model.UserDataModel


interface UserRemoteService {
    suspend fun setUser(userDataModel: UserDataModel)
    suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData, email: String)
    suspend fun updateUserName(name: String, email: String)
    suspend fun updateDob(dob: String, email: String)
    suspend fun updateInterests(interests: List<String>, email: String)
    suspend fun updateWeight(weight: Double, email: String)
    suspend fun updateAboutMe(aboutMe: String, email: String)
    suspend fun updateHeight(height: Double, email: String)
    suspend fun getUser(email: String): UserDataModel?
    suspend fun deleteAccount(email: String)
    suspend fun getUserEmail(): String?
}