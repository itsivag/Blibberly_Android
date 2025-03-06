package com.superbeta.blibberly.onboarding.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.blibberly.user.repo.MUserRepository
import com.superbeta.blibberly_models.PhotoMetaData
import com.superbeta.blibberly_models.UserDataModel
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
                Log.i("OnBoardingViewModel", "User Data" + userState.value.toString())
            } catch (e: Exception) {
                Log.e("OnBoardingViewModel", "Error getting User Data : " + e.printStackTrace())
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
            mUserRepository.setUser(userDataModel)
//            getUser()
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
}