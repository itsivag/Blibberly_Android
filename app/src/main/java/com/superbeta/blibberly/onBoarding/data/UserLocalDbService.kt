package com.superbeta.blibberly.onBoarding.data

import com.superbeta.blibberly.onBoarding.data.model.UserDataModel
import com.superbeta.blibberly.utils.BlibberlyDatabase

class UserLocalDbService(private val db: BlibberlyDatabase) : UserLocalDao {
    override suspend fun getUser(): UserDataModel {
        return db.userLocalDao().getUser()
    }

    override suspend fun setUSer(userDataModel: UserDataModel) {
        return db.userLocalDao().setUSer(userDataModel)
    }

    override suspend fun updateName(newName: String) {
        return db.userLocalDao().updateName(newName)
    }

    override suspend fun updateAge(newAge: Int) {
        return db.userLocalDao().updateAge(newAge)
    }

    override suspend fun updateHeight(newHeight: Double) {
        return db.userLocalDao().updateHeight(newHeight)
    }

    override suspend fun updateWeight(newWeight: Double) {
        return db.userLocalDao().updateWeight(newWeight)
    }

    override suspend fun updateAboutMe(newAboutMe: String) {
        return db.userLocalDao().updateAboutMe(newAboutMe)
    }
}