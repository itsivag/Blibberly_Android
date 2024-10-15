package com.superbeta.blibberly.user.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.google.gson.Gson
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.repo.MUserRepository
import com.superbeta.blibberly.user.repo.MUserRepositoryImpl
import com.superbeta.blibberly.utils.RoomInstanceProvider
import com.superbeta.blibberly_chat.notification.NotificationRepoImpl
import com.superbeta.blibberly_supabase.utils.supabase
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val mUserRepository: MUserRepository) : ViewModel() {

    private val _userState = MutableStateFlow<UserDataModel?>(null)
    val userState: MutableStateFlow<UserDataModel?> = _userState

    private val _blibmojiUrls = MutableStateFlow<List<String>>(emptyList())

    suspend fun getUser() {
        _userState.value = mUserRepository.getUser()
    }

    suspend fun getUserFCMToken(): String {
        return mUserRepository.getUserFCMToken()
    }

    suspend fun setUser(userDataModel: UserDataModel) {
        viewModelScope.launch {
            mUserRepository.setUser(userDataModel)
            getUser()
        }
    }

    suspend fun updateAboutMe(newAboutMe: String) {
        viewModelScope.launch {
            mUserRepository.updateAboutMe(newAboutMe)
        }
    }

    suspend fun updateInterests(newInterests: List<String>) {
        viewModelScope.launch {
            mUserRepository.updateInterests(newInterests)
        }
    }

    fun updatePhotoMetaData(photoMetaData: PhotoMetaData) {
        viewModelScope.launch {
            mUserRepository.updatePhotoMetaData(photoMetaData)
        }
    }

    suspend fun uploadUserToDB() {
        viewModelScope.launch {
            getUser()
            val u: UserDataModel? = userState.value
            if (u != null) {
                try {
                    supabase.from("Users").insert(u)

                    Log.i("Database Upload Successful", "")
                } catch (e: Exception) {
                    Log.e("Database Upload Error", e.toString())
                }
            } else {
                Log.e("Database Upload Error", "User Data is null")

            }
        }
    }

//    fun getBlibMojiUrlsFromStorage(): StateFlow<List<String>> {
//        viewModelScope.launch {
//            val bucket = supabase.storage.from("blibmoji")
//            val files = bucket.list()
//            _blibmojiUrls.value = files.map { bucket.publicUrl(it.name) }
//        }
//        return _blibmojiUrls.asStateFlow()
//    }


    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>, extras: CreationExtras
            ): T {
                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val db = RoomInstanceProvider.getDb(application.applicationContext)
                val notificationRepo = NotificationRepoImpl()
                val mUserRepository = MUserRepositoryImpl(db.userLocalDao(), notificationRepo)
                return UserViewModel(
                    mUserRepository
                ) as T
            }
        }
    }

}