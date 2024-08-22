package com.superbeta.blibberly_auth.domain

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.superbeta.blibberly_auth.utils.AuthState
import com.superbeta.blibberly_auth.utils.UserDataPreferenceKeys
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_supabase.utils.supabase
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.Email
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import io.github.jan.supabase.gotrue.user.UserInfo
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID


class AuthRepositoryImpl(
    private val context: Context,
    private val credentialManager: CredentialManager
) : AuthRepository {

    private val _authState = MutableStateFlow(AuthState.IDLE)

    override suspend fun getAuthState(): MutableStateFlow<AuthState> {
        return _authState
    }

    override suspend fun createUser(mEmail: String, mPassword: String) {
        _authState.value = AuthState.LOADING
        try {
            supabase.auth.signUpWith(Email) {
                email = mEmail
                password = mPassword
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signInWithEmail(mEmail: String, mPassword: String) {
        _authState.value = AuthState.LOADING
        try {
            supabase.auth.signInWith(Email) {
                email = mEmail
                password = mPassword
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun signInWithGoogle() {
        _authState.value = AuthState.LOADING
        Log.i("Auth State", "Starting Google Sign In")

        // Generate a nonce and hash it with sha-256
        // Providing a nonce is optional but recommended
        val rawNonce = UUID.randomUUID()
            .toString() // Generate a random String. UUID should be sufficient, but can also be any other random string.
        val bytes = rawNonce.toString().toByteArray()
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

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val credentialResponse = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credentialResponse.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

                // Handle successful sign-in
                val user = supabase.auth.retrieveUserForCurrentSession(updateSession = true)
                Log.i("Google Sign In User Data => ", user.toString())
                _authState.value = AuthState.SIGNED_IN
                storeUserInDataStore(user)
                Log.i("Auth State", "Ended Google Sign In => " + _authState.value.toString())
                val isUserRegistered = findIfUserRegistered()

                if (isUserRegistered) {
                    _authState.value = AuthState.USER_REGISTERED
                } else {
                    _authState.value = AuthState.USER_NOT_REGISTERED
                }

            } catch (e: GoogleIdTokenParsingException) {
                e.printStackTrace()
            } catch (e: RestException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun storeUserInDataStore(user: UserInfo) {
        Log.i("Storing User Data In Data Store", user.toString())
        context.userPreferencesDataStore.edit { preferences ->
            preferences[UserDataPreferenceKeys.USER_ID] = user.id
            preferences[UserDataPreferenceKeys.USER_EMAIL] = user.email ?: ""
        }
    }

    override suspend fun getUsersFromDataStore(): Flow<String?> {
        return context.userPreferencesDataStore.data.map { preferences ->
            preferences[UserDataPreferenceKeys.USER_EMAIL]
        }
    }

    override suspend fun getUserData(): UserInfo {
        val user = supabase.auth.retrieveUserForCurrentSession(updateSession = true)
        Log.i("Google Sign In User Data => ", user.toString())
        return user
    }

    override suspend fun findIfUserRegistered(): Boolean {
        val user = supabase.from("Users").select {
            filter {
                getUserData().email?.let { eq("email", it) }
            }
        }.decodeSingleOrNull<UserDataModel>()

        return user != null
    }

    override suspend fun forgotPassword() {
        TODO("Not yet implemented")
    }
}