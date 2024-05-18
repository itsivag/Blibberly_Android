package com.superbeta.blibberly.user.repo

import com.superbeta.blibberly.user.data.model.UserDataModel

interface MUserRepository {
    suspend fun getUser(): UserDataModel
    suspend fun setUser(userDataModel: UserDataModel)
    suspend fun updateName(newName: String)
    suspend fun updateAge(newAge: Int)
    suspend fun updateHeight(newHeight: Double)
    suspend fun updateWeight(newWeight: Double)
    suspend fun updateAboutMe(newAboutMe: String)
    suspend fun updateInterests(newInterests: List<String>)
    suspend fun updatePhotoUri(newPhotoUri: String)
}