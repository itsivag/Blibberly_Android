package com.superbeta.blibberly.user.repo

import com.superbeta.blibberly_models.PhotoMetaData
import com.superbeta.blibberly_models.UserDataModel


interface MUserRepository {
    suspend fun getUser(email: String): com.superbeta.blibberly_models.UserDataModel
    suspend fun getUserEmail(): String?
    suspend fun getUserFCMToken(): String
    suspend fun setUser(userDataModel: com.superbeta.blibberly_models.UserDataModel)
    suspend fun setUserToLocalDb(userDataModel: com.superbeta.blibberly_models.UserDataModel?)
    suspend fun setUserToRemote(userDataModel: com.superbeta.blibberly_models.UserDataModel)
    suspend fun updateName(newName: String)
    suspend fun updateDob(newDob: String)
    suspend fun updateAboutMe(newAboutMe: String)
    suspend fun updateInterests(newInterests: List<String>)
    suspend fun updatePhotoMetaData(photoMetaData: com.superbeta.blibberly_models.PhotoMetaData)
    suspend fun deleteLocalUserData()
    suspend fun deleteAccount(email: String)
}