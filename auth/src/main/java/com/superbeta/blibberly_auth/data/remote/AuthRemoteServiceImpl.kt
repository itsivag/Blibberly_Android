package com.superbeta.blibberly_auth.data.remote

import android.util.Log
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_auth.utils.AuthState
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.gotrue.SessionSource
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthRemoteServiceImpl(private val supabase: SupabaseClient) : AuthRemoteService {

    private val _authState = MutableStateFlow(AuthState.NOT_SIGNED_IN)
    private val authState: StateFlow<AuthState> = _authState

    init {
        CoroutineScope(IO).launch {
            updateAuthStatus()
        }
    }

    override fun getAuthState(): StateFlow<AuthState> {
        return authState
    }

    override suspend fun signInWithGoogle(googleIdToken: String, rawNonce: String) {
        supabase.auth.signInWith(IDToken) {
            idToken = googleIdToken
            provider = Google
            nonce = rawNonce
        }
    }

    override suspend fun retrieveSession(): UserInfo {
        return supabase.auth.retrieveUserForCurrentSession()
    }

    override suspend fun findIfUserRegistered(email: String): Boolean {
        val userData = supabase.from("Users").select { filter { UserDataModel::email eq email } }
            .decodeSingleOrNull<UserDataModel>()
//        Log.i("AuthRemoteServiceImpl", "User Data From Supabase" + userData.toString())
        return userData != null
    }

    override suspend fun signInWithEmail(mEmail: String, mPassword: String) {
        supabase.auth.signInWith(Email) {
            email = mEmail
            password = mPassword
        }
    }

    override suspend fun signUpWithEmail(mEmail: String, mPassword: String) {
        supabase.auth.signUpWith(Email) {
            email = mEmail
            password = mPassword

        }
    }

    override suspend fun updateAuthStatus() {
        supabase.auth.sessionStatus.collect {
            when (it) {
                is SessionStatus.Authenticated -> {
//                    println("Received new authenticated session.")
                    when (it.source) {
                        SessionSource.External -> {}
                        is SessionSource.Refresh -> {}
                        is SessionSource.SignIn -> {
                            retrieveSession().apply {
                                if (this.email?.let { it1 -> findIfUserRegistered(it1) } == true) {
//                                    println("signed in success 1")
                                    _authState.value = AuthState.SIGNED_IN
                                } else {
//                                    println("signed up success")
                                    _authState.value = AuthState.SIGNED_UP
                                }
                            }
                        }

                        is SessionSource.SignUp -> {
//                            println("signed up success")
                            _authState.value = AuthState.SIGNED_UP
                        }

                        SessionSource.Storage -> {
                            retrieveSession().apply {
                                if (this.email?.let { it1 -> findIfUserRegistered(it1) } == true) {
//                                    println("signed in success 2")
                                    _authState.value = AuthState.SIGNED_IN
                                } else {
//                                    println("signed up success")
                                    _authState.value = AuthState.SIGNED_UP
                                }
                            }
                        }

                        SessionSource.Unknown -> {}
                        is SessionSource.UserChanged -> {}
                        is SessionSource.UserIdentitiesChanged -> {}
                        SessionSource.AnonymousSignIn -> {}
                    }
                }

                is SessionStatus.NotAuthenticated -> {
                    if (it.isSignOut) {
                        _authState.value = AuthState.SIGNED_OUT
//                        Log.i("AuthRemoteServiceImpl", "User Signed out")
                    } else {
                        _authState.value = AuthState.NOT_SIGNED_IN
//                        Log.i("AuthRemoteServiceImpl", "User Not signed in")
                    }
                }

                SessionStatus.LoadingFromStorage -> {}
                SessionStatus.NetworkError -> {}
            }
        }
    }
}