package com.superbeta.blibberly_auth.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.superbeta.blibberly_auth.domain.AuthRepository
import com.superbeta.blibberly_auth.domain.AuthRepositoryImpl
import com.superbeta.blibberly_auth.utils.AuthState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    //    private var _authState = MutableStateFlow(AuthState.IDLE)
//    val authState: StateFlow<AuthState> = _authState
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
}
