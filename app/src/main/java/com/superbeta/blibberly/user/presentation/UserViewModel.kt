package com.superbeta.blibberly.user.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly.user.repo.MUserRepository
import com.superbeta.blibberly.user.repo.MUserRepositoryImpl
import com.superbeta.blibberly.utils.RoomInstanceProvider

class UserViewModel(val mUserRepository: MUserRepository) : ViewModel() {















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