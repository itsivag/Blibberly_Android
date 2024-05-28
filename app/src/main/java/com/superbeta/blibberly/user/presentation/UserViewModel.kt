package com.superbeta.blibberly.user.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.repo.MUserRepository
import com.superbeta.blibberly.user.repo.MUserRepositoryImpl
import com.superbeta.blibberly.utils.RoomInstanceProvider
import com.superbeta.blibberly.utils.SupabaseInstance
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UserViewModel(private val mUserRepository: MUserRepository) : ViewModel() {

    private val _userState = MutableStateFlow<UserDataModel?>(null)
    val userState: MutableStateFlow<UserDataModel?> = _userState

    suspend fun getUser() {
        _userState.value = mUserRepository.getUser()
    }

    suspend fun setUser(userDataModel: UserDataModel) {
        viewModelScope.launch {
            mUserRepository.setUser(userDataModel)
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

    fun updatePhotoUri(photoUri: String) {
        viewModelScope.launch {
            mUserRepository.updatePhotoUri(photoUri)
        }
    }

    suspend fun uploadUserToDB() {
        viewModelScope.launch {
            val u: UserDataModel? = userState.value
            if (u != null) {
                try {
                    SupabaseInstance.supabase.from("Users").insert(u)
                } catch (e: Exception) {
                    Log.e("Database Upload Error", e.toString())
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application =
                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                val db = RoomInstanceProvider.getDb(application.applicationContext)
                val mUserRepository = MUserRepositoryImpl(db.userLocalDao())
                return UserViewModel(
                    mUserRepository
                ) as T
            }
        }
    }

}