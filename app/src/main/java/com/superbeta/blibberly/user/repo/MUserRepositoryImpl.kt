package com.superbeta.blibberly.user.repo

import android.util.Log
import com.superbeta.blibberly.user.data.local.UserLocalDao
import com.superbeta.blibberly.user.data.remote.UserRemoteService
import com.superbeta.blibberly_chat.notification.NotificationRepo
import com.superbeta.blibberly_models.UserDataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MUserRepositoryImpl(
    private val db: UserLocalDao,
    private val notificationRepo: NotificationRepo,
    private val userRemoteService: UserRemoteService,
) : MUserRepository {

    private val _userState = MutableStateFlow<UserDataModel?>(null)

//    init {
//        CoroutineScope(IO).launch {
//            getUserEmail()
//            _userState.value = getUser("sivacbrf2@gmail.com")
//        }
//    }


    override suspend fun getUser(email: String): UserDataModel {
        val remoteUserData = userRemoteService.getUser(email)
        val localUserData = db.getUser()

        //TODO change this logic
        try {
//            if (localUserData.email.isEmpty()) {
//                setUserToLocalDb(remoteUserData)
//            }

//            if (remoteUserData != null) {
//                if (remoteUserData != localUserData) {
//                setUserToLocalDb(remoteUserData)
//                    setUserToRemote(localUserData)
//                }
//            }
            Log.i("MUserRepositoryImpl", "Remote Data : " + remoteUserData.toString())
            Log.i("MUserRepositoryImpl", "Local Data : " + db.getUser().toString())
        } catch (e: Exception) {
            Log.e("MUserRepositoryImpl", "Error getting current user : $e")
        }

        return remoteUserData!!
    }

    override suspend fun getUserEmail(): String? {
        return userRemoteService.getUserEmail()
    }

    override suspend fun getUserFCMToken(): String {
        return notificationRepo.getFCMToken()
    }

    override suspend fun setUser(userDataModel: UserDataModel) {
        setUserToLocalDb(userDataModel)
//        setUserToRemote(userDataModel)
    }


    override suspend fun setUserToLocalDb(userDataModel: UserDataModel?) {
        userDataModel?.let { db.setUser(it) }
    }

    override suspend fun setUserToRemote(userDataModel: UserDataModel) {
        userRemoteService.setUser(userDataModel)
    }

    override suspend fun updateName(newName: String) {
        db.updateName(newName)
        _userState.value?.let { userRemoteService.updateUserName(newName, it.email) }
    }

    override suspend fun updateDob(newDob: String) {
        _userState.value?.let { userRemoteService.updateDob(newDob, it.email) }
        db.updateAge(newDob)
    }

//    override suspend fun updateHeight(newHeight: Double) {
//        CoroutineScope(IO).launch {
//            _userState.value?.let { userRemoteService.updateHeight(newHeight, it.email) }
//            db.updateHeight(newHeight)
//        }
//    }

//    override suspend fun updateWeight(newWeight: Double) {
//        CoroutineScope(IO).launch {
//            _userState.value?.let { userRemoteService.updateWeight(newWeight, it.email) }
////            db.updateWeight(newWeight)
//        }
//    }

    override suspend fun updateAboutMe(newAboutMe: String) {
        CoroutineScope(IO).launch {
            _userState.value?.let { userRemoteService.updateAboutMe(newAboutMe, it.email) }
            db.updateAboutMe(newAboutMe)
        }
    }

    override suspend fun updateInterests(newInterests: List<String>) {
        CoroutineScope(IO).launch {
            _userState.value?.let { userRemoteService.updateInterests(newInterests, it.email) }
            db.updateInterests(newInterests)
        }
    }

    override suspend fun updatePhotoMetaData(photoMetaData: com.superbeta.blibberly_models.PhotoMetaData) {
        CoroutineScope(IO).launch {
            db.updatePhotoMetaData(photoMetaData)
            _userState.value?.let {
                userRemoteService.updatePhotoMetaData(
                    photoMetaData,
                    it.email
                )
            }
        }
    }

    override suspend fun deleteLocalUserData() {
        CoroutineScope(IO).launch {
            Log.i("MUserRepositoryImpl", "Logging out user!")
            db.deleteUserInfo()
        }
    }

    override suspend fun deleteAccount(email: String) {
        CoroutineScope(IO).launch {
            Log.i("MUserRepositoryImpl", "Deleting User!")
            userRemoteService.deleteAccount(email)
            db.deleteUserInfo()

        }
    }


}