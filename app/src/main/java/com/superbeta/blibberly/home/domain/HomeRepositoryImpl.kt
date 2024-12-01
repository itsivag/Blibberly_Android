package com.superbeta.blibberly.home.domain

import com.superbeta.blibberly.user.data.model.UserDataModel

class HomeRepositoryImpl : HomeRepository {
    override suspend fun getProfiles(): List<UserDataModel> {
        TODO("Not yet implemented")
    }
}