package com.superbeta.blibberly_auth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.superbeta.blibberly_auth.ui.theme.AuthTheme
import kotlinx.coroutines.launch

class AuthActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        credentialManager = CredentialManager.create(baseContext)

        launchCredentialManager()
        enableEdgeToEdge()
        setContent {
            AuthTheme {
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Column(
//                        Modifier
//                            .padding(innerPadding)
//                            .fillMaxWidth()
//                    ) {
//                        Button(onClick = {
//                            loginWithBrowser()
//                        }) {
//                            Text(text = "Sign In With Google")
//                        }
//                    }

//                    val authViewModel = getViewModel<AuthViewModel>()
//                    val authState by authViewModel.userInfoState.collectAsStateWithLifecycle()
//                    var startNavRoute by remember {
//                        mutableStateOf(Routes.OnBoarding.graph)
//                    }
//                    LaunchedEffect(authState) {
//                        when (authState) {
//                            is UserInfoState.Failed -> {}
//                            is UserInfoState.Success -> {
//                                startActivity(Intent(this,AuthActivity::class.java))
//                            }
//                            null -> {}
//                        }
//                    }
//                    AuthNavHost(
//                        navController = rememberNavController(),
//                        modifier = Modifier.padding(innerPadding)
//                    )
                }
//            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        var currentUser = auth.currentUser
        Log.i(this.localClassName, "Current User : $currentUser")
//        updateUI(currentUser);
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

