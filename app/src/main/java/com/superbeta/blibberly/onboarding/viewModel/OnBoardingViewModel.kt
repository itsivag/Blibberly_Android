package com.superbeta.blibberly.onboarding.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.blibberly.user.repo.MUserRepository
import com.superbeta.blibberly_auth.model.PhotoMetaData
import com.superbeta.blibberly_auth.model.UserDataModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OnBoardingViewModel(private val mUserRepository: MUserRepository) : ViewModel() {

    private val _userState = MutableStateFlow<UserDataModel?>(null)
    val userState: StateFlow<UserDataModel?> = _userState

    init {
        getUser()
    }

    fun getUser() {
        viewModelScope.launch(IO) {
            try {
                _userState.value = getUserEmail()?.let { mUserRepository.getUser(it) }
                Log.i("UserViewModel", "User Data" + userState.value.toString())
            } catch (e: Exception) {
                Log.e("UserViewModel", "Error getting User Data : " + e.printStackTrace())
            }
        }
    }

    suspend fun getUserEmail(): String? {
        return mUserRepository.getUserEmail()
    }

    suspend fun getUserFCMToken(): String {
        return mUserRepository.getUserFCMToken()
    }

    suspend fun setUser(userDataModel: UserDataModel) {
        viewModelScope.launch(IO) {
            //TODO delegate to repo have a single func
            mUserRepository.setUserToLocalDb(userDataModel)
            mUserRepository.setUserToRemote(userDataModel)
            getUser()
        }
    }

    suspend fun updateAboutMe(newAboutMe: String) {
        viewModelScope.launch(IO) {
            mUserRepository.updateAboutMe(newAboutMe)
        }
    }

    suspend fun updateName(name: String) {
        viewModelScope.launch(IO) {
            mUserRepository.updateName(name)
        }
    }

    suspend fun updateInterests(newInterests: List<String>) {
        viewModelScope.launch(IO) {
            mUserRepository.updateInterests(newInterests)
        }
    }

    fun updatePhotoMetaData(photoMetaData: PhotoMetaData) {
        viewModelScope.launch(IO) {
            mUserRepository.updatePhotoMetaData(photoMetaData)
        }
    }

//
//    suspend fun uploadUserToDB() {
//        viewModelScope.launch(IO) {
//            getUser()
//            val u: UserDataModel? = userState.value
//            if (u != null) {
//                //TODO make changes to supa upload
//                mUserRepository.setUserToRemote(u)
//            } else {
//                Log.e("Database Upload Error", "User Data is null")
//
//            }
//        }
//    }

//    suspend fun deleteLocalUserInfo() {
//        viewModelScope.launch(IO) {
//            mUserRepository.deleteLocalUserData()
//        }
//    }
//
//    suspend fun deleteAccount(email: String) {
//        viewModelScope.launch(IO) {
//            mUserRepository.deleteAccount(email)
//        }
//    }


//    fun getBlibMojiUrlsFromStorage(): StateFlow<List<String>> {
//        viewModelScope.launch {
//            val bucket = supabase.storage.from("blibmoji")
//            val files = bucket.list()
//            _blibmojiUrls.value = files.map { bucket.publicUrl(it.name) }
//        }
//        return _blibmojiUrls.asStateFlow()
//    }
}