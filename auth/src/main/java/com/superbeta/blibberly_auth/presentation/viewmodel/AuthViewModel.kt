package com.superbeta.blibberly_auth.presentation.viewmodel

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.superbeta.blibberly_auth.domain.AuthRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private val _userInfoState = MutableStateFlow<UserInfoState?>(null)
    val userInfoState: StateFlow<UserInfoState?> = _userInfoState.asStateFlow()

    init {
        getUser()
    }

    fun signInWithGoogle(activity: Activity) {
        viewModelScope.launch(IO) {
            authRepository.signInWithGoogle(activity)
        }
    }

    fun getUser() {
        viewModelScope.launch(IO) {
            val user = authRepository.getUserData()
            if (user != null) {
                val isUserRegistered = user.email?.let { authRepository.findIfUserRegistered(it) }
                _userInfoState.value =
                    if (isUserRegistered == true) UserInfoState.Success(user) else UserInfoState.NotRegistered
            } else {
                UserInfoState.Failed
            }
        }
    }
}

sealed class UserInfoState {
    data class Success(val user: FirebaseUser) : UserInfoState()
    data object Failed : UserInfoState()
    data object NotRegistered : UserInfoState()
}