package com.superbeta.blibberly.user.repo

import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.StateFlow

interface MUserRepository {
    suspend fun getUser(email: String): UserDataModel
    suspend fun getUserEmail(): String?
    suspend fun getUserFCMToken(): String
    suspend fun setUserToLocalDb(userDataModel: UserDataModel?)
    suspend fun setUserToRemote(userDataModel: UserDataModel)
    suspend fun updateName(newName: String)
    suspend fun updateAge(newAge: Int)
    suspend fun updateHeight(newHeight: Double)
//    suspend fun updateWeight(newWeight: Double)
    suspend fun updateAboutMe(newAboutMe: String)
    suspend fun updateInterests(newInterests: List<String>)
    suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData)
    suspend fun deleteLocalUserData()
    suspend fun deleteAccount(email: String)
}