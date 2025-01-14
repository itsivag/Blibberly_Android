package com.superbeta.blibberly_auth.presentation.viewmodel

import android.util.Log
import androidx.credentials.Credential
import androidx.credentials.CustomCredential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.superbeta.blibberly_auth.domain.AuthRepository
import com.superbeta.blibberly_auth.utils.AuthState
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

//    suspend fun signInWithGoogle() {
//        viewModelScope.launch {
//            authRepository.signInWithGoogle()
//        }
//    }
//
//    suspend fun getUserEmailFromDataStore(): Flow<String?> {
//        return authRepository.getUsersFromDataStore()
//    }

    suspend fun logOut() {
        viewModelScope.launch {
            authRepository.logOut()
        }
    }

    fun onSignInWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                try {
                    val googleIdTokenCredential =
                        GoogleIdTokenCredential.createFrom(credential.data)
                    authRepository.signInWithGoogle(googleIdTokenCredential.idToken)
                } catch (e: Exception) {
                    Log.e("AuthViewModel", "Failed to process Google credential", e)
                    // Consider adding a way to show error to user
                }
            } else {
                Log.e("AuthViewModel", "Invalid credential type: ${credential.type}")
                // Consider adding a way to show error to user
            }
        }
    }

    fun onSignUpWithGoogle(credential: Credential) {
        viewModelScope.launch {
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                authRepository.linkAccountWithGoogle(googleIdTokenCredential.idToken)

            } else {
                //TODO explain users what went wrong
                Log.e("AuthViewModel", "Invalid credential type")
            }
        }
    }


}
