package com.superbeta.blibberly_auth.domain

import android.content.Context
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.superbeta.blibberly_auth.data.local.AuthDataStoreService
import com.superbeta.blibberly_auth.data.remote.AuthRemoteService
import com.superbeta.blibberly_auth.utils.AuthState
//import com.superbeta.blibberly_supabase.utils.supabase
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID


class AuthRepositoryImpl(
    private val context: Context,
    private val credentialManager: CredentialManager,
    private val authRemoteService: AuthRemoteService,
    private val authDataStoreService: AuthDataStoreService
) : AuthRepository {

    //    private val _authState = MutableStateFlow(AuthState.IDLE)
    override fun getAuthState(): StateFlow<AuthState> {
        return authRemoteService.getAuthState()
    }


//    init {
//        CoroutineScope(IO).launch {
//            updateAuthState()
//        }
//    }

//    override suspend fun updateAuthState() {
////        if (_authState.value == AuthState.SIGNED_IN) {
//        getUsersFromDataStore().collect { user ->
//            if (!user.isNullOrEmpty()) {
//                val isUserRegistered = findIfUserRegistered(user)
//                if (isUserRegistered) {
//                    //signed in and registered
//                    Log.i("AuthRepositoryImpl", "Registered")
//                    _authState.value = AuthState.USER_REGISTERED
//                } else {
//                    //signed in and not registered
//                    Log.i("AuthRepositoryImpl", "Not Registered")
//                    _authState.value = AuthState.USER_NOT_REGISTERED
//                }
//            } else {
//                _authState.value = AuthState.SIGNED_OUT
//            }
//            Log.i("AuthRepositoryImpl", "AUTH STATE : " + _authState.value.toString())
//        }
////        }
//    }

//    override fun getAuthState(): StateFlow<AuthState> = authState


    override suspend fun createUser(mEmail: String, mPassword: String) {
//        _authState.value = AuthState.LOADING
        try {
            authRemoteService.signUpWithEmail(mEmail, mPassword)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signInWithEmail(mEmail: String, mPassword: String) {
//        _authState.value = AuthState.LOADING
        try {
            authRemoteService.signInWithEmail(mEmail, mPassword)
        } catch (e: Exception) {
//            _authState.value = AuthState.ERROR
            Log.e("AuthRepositoryImpl", "Failed to sign in with email: $e")
        }
    }

    override suspend fun signInWithGoogle() {
//        _authState.value = AuthState.LOADING
        Log.i("AuthRepositoryImpl", "Starting Google Sign In")

        // Generate a nonce and hash it with sha-256
        // Providing a nonce is optional but recommended
        val rawNonce = UUID.randomUUID()
            .toString() // Generate a random String. UUID should be sufficient, but can also be any other random string.
        val bytes = rawNonce.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        val hashedNonce =
            digest.fold("") { str, it -> str + "%02x".format(it) } // Hashed nonce to be passed to Google sign-in


        val googleIdOption: GetGoogleIdOption =
            GetGoogleIdOption.Builder().setFilterByAuthorizedAccounts(false)
                .setServerClientId("612314404654-m2vafg7gdboioqqhitb8ctt8pqene9tl.apps.googleusercontent.com")
                .setNonce(hashedNonce) // Provide the nonce if you have one
                .build()

        val request: GetCredentialRequest =
            GetCredentialRequest.Builder().addCredentialOption(googleIdOption).build()

        CoroutineScope(IO).launch {
            try {
                val credentialResponse = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credentialResponse.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

//                supabase.auth.signInWith(IDToken) {
//                    idToken = googleIdToken
//                    provider = Google
//                    nonce = rawNonce
//                }

                authRemoteService.signInWithGoogle(googleIdToken, rawNonce)

                // Handle successful sign-in
//                val user = supabase.auth.retrieveUserForCurrentSession(updateSession = true)
                val user = authRemoteService.retrieveSession()
                Log.i("Google Sign In User Data => ", user.toString())
//                _authState.value = AuthState.SIGNED_IN
                storeUserInDataStore(user)
//                Log.i("AuthRepositoryImpl", "AUTH STATE" + _authState.value.toString())
//                if (_authState.value == AuthState.SIGNED_IN) {
//                    updateAuthState()
//                }
//                val isUserRegistered = findIfUserRegistered()

//                if (isUserRegistered) {
//                    _authState.value = AuthState.USER_REGISTERED
//                } else {
//                    _authState.value = AuthState.USER_NOT_REGISTERED
//                }

            } catch (e: GoogleIdTokenParsingException) {
                e.printStackTrace()
            } catch (e: RestException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun storeUserInDataStore(user: UserInfo) =
        authDataStoreService.setUserData(user)


//    override suspend fun getUsersFromDataStore(): Flow<String?> = authDataStoreService.getUserData()

    override suspend fun logOut() {
        Log.i("AuthRepositoryImpl", "Deleting data stores")
        authDataStoreService.deleteUserData()
//        _authState.value = AuthState.SIGNED_OUT
    }

    override suspend fun signUpWithEmail(email: String, password: String) {
        try {
            authRemoteService.signUpWithEmail(email, password)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to sign up with email: $e")
        }
    }

    override suspend fun retrieveSession(): UserInfo? {
        try {
            return authRemoteService.retrieveSession()
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Failed to retrieve session: $e")
            return null
        }
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