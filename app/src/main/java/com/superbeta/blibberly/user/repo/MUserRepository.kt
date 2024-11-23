package com.superbeta.blibberly.user.repo

import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import kotlinx.coroutines.flow.Flow

interface MUserRepository {
    suspend fun getUser(): UserDataModel
    suspend fun getUserEmail(): String?
    suspend fun getUserFCMToken(): String
    suspend fun setUserToLocalDb(userDataModel: UserDataModel?)
    suspend fun setUserToRemote(userDataModel: UserDataModel)
    suspend fun updateName(newName: String)
    suspend fun updateAge(newAge: Int)
    suspend fun updateHeight(newHeight: Double)
    suspend fun updateWeight(newWeight: Double)
    suspend fun updateAboutMe(newAboutMe: String)
    suspend fun updateInterests(newInterests: List<String>)
    suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData)
}