package com.superbeta.blibberly.user.repo

import com.superbeta.blibberly.user.data.local.UserLocalDao
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel


class MUserRepositoryImpl(private val db: UserLocalDao) : MUserRepository {
    override suspend fun getUser(): UserDataModel {
        return db.getUser()
    }

    override suspend fun setUser(userDataModel: UserDataModel) {
        return db.setUser(userDataModel)
    }

    override suspend fun updateName(newName: String) {
        return db.updateName(newName)
    }

    override suspend fun updateAge(newAge: Int) {
        return db.updateAge(newAge)
    }

    override suspend fun updateHeight(newHeight: Double) {
        return db.updateHeight(newHeight)
    }

    override suspend fun updateWeight(newWeight: Double) {
        return db.updateWeight(newWeight)
    }

    override suspend fun updateAboutMe(newAboutMe: String) {
        return db.updateAboutMe(newAboutMe)
    }

    override suspend fun updateInterests(newInterests: List<String>) {
        return db.updateInterests(newInterests)
    }

    override suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData) {
        return db.updatePhotoMetaData(photoMetaData)
    }


}