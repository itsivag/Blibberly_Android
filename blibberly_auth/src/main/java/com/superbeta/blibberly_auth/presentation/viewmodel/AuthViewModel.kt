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
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    private var _authState = MutableStateFlow(AuthState.IDLE)
    val authState: MutableStateFlow<AuthState> = _authState

    init {
        viewModelScope.launch {
            authRepository.getAuthState().collect { state ->
                _authState.value = state
                Log.i("AuthViewModel", "Auth state updated: $state")
            }
        }
    }

    suspend fun signInWithGoogle() {
        viewModelScope.launch {
            authRepository.signInWithGoogle()
        }
    }

    suspend fun getUserEmailFromDataStore(): Flow<String?> {
        return authRepository.getUsersFromDataStore()
    }

//    companion object {
//        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(
//                modelClass: Class<T>, extras: CreationExtras
//            ): T {
//                val application =
//                    extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
//
//                val credentialManager = CredentialManager.create(application)
//                val authRepositoryImpl = AuthRepositoryImpl(application, credentialManager)
//
//                return AuthViewModel(
//                    authRepositoryImpl
//                ) as T
//            }
//        }
//    }
}
