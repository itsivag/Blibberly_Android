package com.superbeta.blibberly.user.repo

import com.superbeta.blibberly_auth.model.PhotoMetaData
import com.superbeta.blibberly_auth.model.UserDataModel


interface MUserRepository {
    suspend fun getUser(email: String): UserDataModel
    suspend fun getUserEmail(): String?
    suspend fun getUserFCMToken(): String
    suspend fun setUserToLocalDb(userDataModel: UserDataModel?)
    suspend fun setUserToRemote(userDataModel: UserDataModel)
    suspend fun updateName(newName: String)
    suspend fun updateDob(newDob: String)
//    suspend fun updateHeight(newHeight: Double)
    suspend fun updateAboutMe(newAboutMe: String)
    suspend fun updateInterests(newInterests: List<String>)
    suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData)
    suspend fun deleteLocalUserData()
    suspend fun deleteAccount(email: String)
}