package com.superbeta.blibberly_auth.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.superbeta.blibberly_auth.domain.AuthRepository
import com.superbeta.blibberly_auth.utils.AuthState
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    val authState: StateFlow<AuthState> = authRepository.getAuthState()
    private var account: Auth0 = Auth0(
        "mpyhQkJZodI8HMKMpUxTTRlLZRtZw9k4",
        "dev-ydixucx70csticsi.us.auth0.com"
    )


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

    suspend fun retrieveSession(): UserInfo? {
        return authRepository.retrieveSession()
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

    fun loginWithBrowser(context: Context) {
        viewModelScope.launch(IO) {
            WebAuthProvider.login(account)
                .withScheme("com.superbeta.blibberly")
                .withScope("openid profile email")
                .start(context, object : Callback<Credentials, AuthenticationException> {
                    override fun onFailure(error: AuthenticationException) {
                        Log.i("AuthActivity", "Failed to Log in $error")
                    }

                    override fun onSuccess(result: Credentials) {
                        val accessToken = result.accessToken
                        Log.i(
                            "AuthActivity",
                            "Logged In User Email : " + result.user.email.toString()
                        )
                    }
                })
        }
    }

}
