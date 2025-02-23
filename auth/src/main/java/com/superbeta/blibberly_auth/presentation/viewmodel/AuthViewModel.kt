package com.superbeta.blibberly_auth.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import com.superbeta.blibberly_auth.domain.AuthRepository
import com.superbeta.blibberly_auth.utils.AuthState
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import java.lang.Thread.State

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {
    //    val authState: StateFlow<AuthState> = authRepository.getAuthState()
    private val account: Auth0 = Auth0(
        "mpyhQkJZodI8HMKMpUxTTRlLZRtZw9k4",
        "dev-ydixucx70csticsi.us.auth0.com"
    )
    private val client = AuthenticationAPIClient(account)
    private val _accessToken = MutableStateFlow<String?>(null)
    private val accessToken: StateFlow<String?> = _accessToken.asStateFlow()


    private val _userInfoState = MutableStateFlow<UserInfoState?>(null)
    val userInfoState: StateFlow<UserInfoState?> = _userInfoState.asStateFlow()

    init {
        viewModelScope.launch(IO) {
            getAccessToken()
            accessToken.collectLatest { token ->
                if (!token.isNullOrBlank()) {
                    Log.d("AuthViewModel", "Access Token : ${accessToken.value}")
                    getUserInfo(token)
                }
            }
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
                        setAccessToken(result.accessToken)
                        Log.i(
                            "AuthActivity",
                            "Logged In User Email : " + result.user.email.toString()
                        )
                    }
                })
        }
    }

    private fun setAccessToken(token: String) {
        viewModelScope.launch(IO) {
            authRepository.setAccessToken(token)
        }
    }


    private fun getAccessToken() {
        viewModelScope.launch(IO) {
            _accessToken.value = authRepository.getAccessToken()
//            Log.i("AuthViewModel", "Access Token : ${_accessToken.value}")
        }
    }


    private fun getUserInfo(token: String) {
        viewModelScope.launch(IO) {
            try {
                Log.d("AuthViewModel", "getUserInfo() called")
                client.userInfo(token)
                    .start(object : Callback<UserProfile, AuthenticationException> {
                        override fun onFailure(error: AuthenticationException) {
                            _userInfoState.value = UserInfoState.Failed(error)
                            Log.e("AuthViewModel", "Failed to fetch user info$error")
                        }

                        override fun onSuccess(result: UserProfile) {
                            _userInfoState.value = UserInfoState.Success(result)
                            Log.e("AuthViewModel", "User info: ${result.email}")
                        }

                    })
            } catch (e: Exception) {
                Log.e("AuthViewModel", e.toString())
            }
        }
    }
}


sealed class UserInfoState {
    data class Success(val user: UserProfile) : UserInfoState()
    data class Failed(val error: AuthenticationException) : UserInfoState()
}