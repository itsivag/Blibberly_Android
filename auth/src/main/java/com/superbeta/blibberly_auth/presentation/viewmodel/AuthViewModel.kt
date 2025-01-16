package com.superbeta.blibberly_auth.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.blibberly_auth.domain.AuthRepository
import com.superbeta.blibberly_auth.utils.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val authState: StateFlow<AuthState> = authRepository.getAuthState()

    init {
        Log.i("AuthViewModel", "AUTH STATE :$authState")
    }
//        viewModelScope.launch {
//            authRepository.getAuthState().collect { state ->
//                _authState.value = state
//                Log.i("AuthViewModel", "Auth state updated: $state")
//            }
//        }
//    }
//    suspend fun getAuthState() {
//        viewModelScope.launch {
//            authRepository.getAuthState().collect { state ->
//                _authState.value = state
//            }
//        }

//    }

    suspend fun signInWithGoogle() {
        viewModelScope.launch {
            authRepository.signInWithGoogle()
        }
    }

    suspend fun getUserEmailFromDataStore(): Flow<String?> {
        return authRepository.getUsersFromDataStore()
    }

    suspend fun logOut() {
        viewModelScope.launch {
            authRepository.logOut()
        }
    }

    fun signInWithEmail(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signInWithEmail(email, password)
        }
    }

    fun signUpWithEmail(email: String, password: String) {
        viewModelScope.launch {
            authRepository.signUpWithEmail(email, password)
        }
    }


}
