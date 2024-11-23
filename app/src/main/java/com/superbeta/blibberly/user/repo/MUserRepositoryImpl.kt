package com.superbeta.blibberly.user.repo

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.superbeta.blibberly.user.data.local.UserLocalDao
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.data.remote.UserRemoteService
import com.superbeta.blibberly_auth.utils.UserDataPreferenceKeys
import com.superbeta.blibberly_chat.notification.NotificationRepo
import com.superbeta.blibberly_chat.notification.NotificationRepoImpl
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map


class MUserRepositoryImpl(
    private val db: UserLocalDao,
    private val notificationRepo: NotificationRepo,
    private val userPreferencesDataStore: DataStore<Preferences>,
    private val userRemoteService: UserRemoteService
) : MUserRepository {
    override suspend fun getUser(): UserDataModel {

        val remoteUserData = getUserEmail()?.let { userRemoteService.getUser(it) }
        val localUserData = db.getUser()

        if (remoteUserData != null) {
            if (remoteUserData != localUserData) {
                setUserToLocalDb(remoteUserData)
            }
        }
//        Log.i("Database Upload Remote", remoteUserData.toString())
//        Log.i("Database Upload Local", db.getUser().toString())

        return db.getUser()
    }

    override suspend fun getUserEmail(): String? {
        return userPreferencesDataStore.data.map { preferences ->
            preferences[UserDataPreferenceKeys.USER_EMAIL]
        }.firstOrNull()
    }

    override suspend fun getUserFCMToken(): String {
        return notificationRepo.getFCMToken()
    }


    override suspend fun setUserToLocalDb(userDataModel: UserDataModel?) {
        userDataModel?.let { db.setUser(it) }
    }

    override suspend fun setUserToRemote(userDataModel: UserDataModel) {

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