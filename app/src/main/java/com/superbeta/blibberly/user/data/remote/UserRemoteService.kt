package com.superbeta.blibberly.user.data.remote

import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import io.github.jan.supabase.gotrue.user.UserInfo


interface UserRemoteService {
    suspend fun setUser(userDataModel: UserDataModel)
    suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData, email: String)
    suspend fun updateUserName(name: String, email: String)
    suspend fun updateAge(age: Int, email: String)
    suspend fun updateInterests(interests: List<String>, email: String)
    suspend fun updateWeight(weight: Double, email: String)
    suspend fun updateAboutMe(aboutMe: String, email: String)
    suspend fun updateHeight(height: Double, email: String)
    suspend fun getUser(email: String): UserDataModel?
    suspend fun deleteAccount(email: String)
//    suspend fun retrieveSession(): UserInfo?
}