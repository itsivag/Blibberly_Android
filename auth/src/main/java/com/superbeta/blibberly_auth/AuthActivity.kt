package com.superbeta.blibberly_auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.presentation.viewmodel.UserInfoState
import com.superbeta.blibberly_auth.ui.theme.AuthTheme
import com.superbeta.blibberly_auth.utils.Routes
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class AuthActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel = getViewModel<AuthViewModel>()

        auth = Firebase.auth
        credentialManager = CredentialManager.create(baseContext)

        enableEdgeToEdge()
        setContent {
            AuthTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val authState by authViewModel.userInfoState.collectAsStateWithLifecycle()
                    var startNavRoute by remember {
                        mutableStateOf(Routes.OnBoarding.graph)
                    }

                    val navController = rememberNavController()
                    LaunchedEffect(authState) {
                        when (authState) {
                            UserInfoState.Failed -> {
                                Log.i(
                                    "AuthActivity",
                                    "Failed to Sign In"
                                )
                            }

                            is UserInfoState.Success -> {
                                Log.i(
                                    "AuthActivity",
                                    "Current User : ${(authState as? UserInfoState.Success)?.user?.email ?: "Unable to fetch"}"
                                )

                                navController.navigate("myapp://profile/123")
                            }

                            null -> {}
                        }
                    }

//                    AuthNavHost(
//                        navController = navController,
//                        modifier = Modifier.padding(innerPadding),
//                        signInWithGoogle = { launchCredentialManager() }
//                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
//        val currentUser = auth.currentUser
//        authViewModel.getUser()
//        Log.i(this.localClassName, "Current User : ${currentUser?.email}")
    }

    private fun launchCredentialManager() {
        // [START create_credential_manager_request]
        // Instantiate a Google sign-in request
        val googleIdOption = GetGoogleIdOption.Builder()
            // Your server's client ID, not your Android client ID.
            .setServerClientId(getString(R.string.default_web_client_id))
            // Only show accounts previously used to sign in.
            .setFilterByAuthorizedAccounts(false)
            .build()

        // Create the Credential Manager request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()
        // [END create_credential_manager_request]

        lifecycleScope.launch {
            try {
                // Launch Credential Manager UI
                val result = credentialManager.getCredential(
                    context = baseContext,
                    request = request
                )

                // Extract credential from the result returned by `Credential Manager
                handleSignIn(result.credential)
            } catch (e: GetCredentialException) {
                Log.e(
                    "MainActivity",
                    "Couldn't retrieve user's credentials: ${e.localizedMessage}"
                )
            }
        }
    }

    private fun handleSignIn(credential: Credential) {
        // Check if credential is of type Google ID
        if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
            // Create Google ID Token
            val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

            // Sign in to Firebase with using the token
            firebaseAuthWithGoogle(googleIdTokenCredential.idToken)
        } else {
            Log.w(this.localClassName, "Credential is not of type Google ID!")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(this.localClassName, "signInWithCredential:success")
                    val user = auth.currentUser
                    Log.i(this.localClassName, "Current User : ${user?.email}")

                    //                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user
                    Log.w(this.localClassName, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
                }
            }
    }

}

