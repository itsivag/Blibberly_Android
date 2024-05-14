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
}