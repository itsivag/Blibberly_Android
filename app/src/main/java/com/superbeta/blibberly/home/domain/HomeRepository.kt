package com.superbeta.blibberly.home.domain

import com.superbeta.blibberly.user.data.model.UserDataModel

interface HomeRepository {
    suspend fun getProfiles() : List<UserDataModel>
}