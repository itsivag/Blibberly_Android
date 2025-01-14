package com.superbeta.blibberly_auth.domain

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.superbeta.blibberly_auth.data.local.AuthDataStoreService
import com.superbeta.blibberly_auth.data.remote.AuthRemoteService
import com.superbeta.blibberly_auth.utils.AuthState
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class AuthRepositoryImpl(
    private val context: Context,
    private val credentialManager: CredentialManager,
    private val authRemoteService: AuthRemoteService,
    private val authDataStoreService: AuthDataStoreService
) : AuthRepository {

    private val _authState = MutableStateFlow(AuthState.IDLE)
    private val authState: StateFlow<AuthState> = _authState

    init {
        CoroutineScope(IO).launch {
            updateAuthState()
        }
    }

    override suspend fun updateAuthState() {
//        if (_authState.value == AuthState.SIGNED_IN) {
        getUsersFromDataStore().collect { user ->
            if (!user.isNullOrEmpty()) {
                val isUserRegistered = findIfUserRegistered(user)
                if (isUserRegistered) {
                    //signed in and registered
                    Log.i("AuthRepositoryImpl", "Registered")
                    _authState.value = AuthState.USER_REGISTERED
                } else {
                    //signed in and not registered
                    Log.i("AuthRepositoryImpl", "Not Registered")
                    _authState.value = AuthState.USER_NOT_REGISTERED
                }
            } else {
                _authState.value = AuthState.SIGNED_OUT
            }
            Log.i("AuthRepositoryImpl", "AUTH STATE : " + _authState.value.toString())
        }
//        }
    }

    override fun getAuthState(): StateFlow<AuthState> = authState

    override suspend fun createUser(mEmail: String, mPassword: String) {
        _authState.value = AuthState.LOADING
        try {
//            supabase.auth.signUpWith(Email) {
//                email = mEmail
//                password = mPassword
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signInWithEmail(mEmail: String, mPassword: String) {
        _authState.value = AuthState.LOADING
        try {
//            supabase.auth.signInWith(Email) {
//                email = mEmail
//                password = mPassword
//            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signInWithGoogle(idToken: String) {
//        val firebaseCred = GoogleAuthProvider.getCredential(idToken, null)
//        com.google.firebase.Firebase.auth.signInWithCredential(firebaseCred).await()
//        authRemoteService.signInWithGoogle(idToken)
    }

    private suspend fun storeUserInDataStore(user: UserInfo) =
        authDataStoreService.setUserData(user)


    override suspend fun getUsersFromDataStore(): Flow<String?> = authDataStoreService.getUserData()

    override suspend fun logOut() {
        Log.i("AuthRepositoryImpl", "Deleting data stores")
        authDataStoreService.deleteUserData()
        _authState.value = AuthState.SIGNED_OUT
    }

    override suspend fun linkAccountWithGoogle(idToken: String) {
        val firebaseCred = GoogleAuthProvider.getCredential(idToken, null)
        Firebase.auth.currentUser!!.linkWithCredential(firebaseCred).await()
    }


    override suspend fun getUserData(): UserInfo {
        val user = authRemoteService.retrieveSession()
        Log.i("Google Sign In User Data => ", user.toString())
        return user
    }

    override suspend fun findIfUserRegistered(email: String): Boolean =
        authRemoteService.findIfUserRegistered(email)

    override suspend fun forgotPassword() {}
}