package com.superbeta.blibberly_auth.domain

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.core.content.ContextCompat.getString
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.superbeta.blibberly_auth.R
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


class AuthRepositoryImpl(
    private val context: Context,
    private val credentialManager: CredentialManager,
    private val authRemoteService: AuthRemoteService,
) : AuthRepository {

    private val auth: FirebaseAuth = Firebase.auth

    override suspend fun findIfUserRegistered(email: String): Boolean =
        authRemoteService.findIfUserRegistered(email)

    override suspend fun signInWithGoogle(activity: Activity) {

        fun firebaseAuthWithGoogle(idToken: String) {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            auth.signInWithCredential(credential)
                .addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("AuthRepositoryImpl", "signInWithCredential:success")
                        val user = auth.currentUser
                        Log.i("AuthRepositoryImpl", "Current User : ${user?.email}")

                        //                    updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user
                        Log.w("AuthRepositoryImpl", "signInWithCredential:failure", task.exception)
                        //                    updateUI(null)
                    }
                }
        }

        fun handleSignIn(credential: Credential) {
            // Check if credential is of type Google ID
            if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                // Create Google ID Token
                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(credential.data)

                // Sign in to Firebase with using the token
                firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
            } else {
                Log.w("AuthRepositoryImpl", "Credential is not of type Google ID!")
            }
        }

        fun launchCredentialManager() {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(getString(context, R.string.default_web_client_id))
                .setFilterByAuthorizedAccounts(false)
                .build()

            // Create the Credential Manager request
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            CoroutineScope(IO).launch {
                try {
                    // Launch Credential Manager UI
                    val result = credentialManager.getCredential(
                        context = context,
                        request = request
                    )

                    handleSignIn(result.credential)
                } catch (e: GetCredentialException) {
                    Log.e(
                        "AuthRepositoryImpl",
                        "Couldn't retrieve user's credentials: ${e.localizedMessage}"
                    )
                }
            }
        }

        launchCredentialManager()
    }

    override suspend fun getUserData() = auth.currentUser
}