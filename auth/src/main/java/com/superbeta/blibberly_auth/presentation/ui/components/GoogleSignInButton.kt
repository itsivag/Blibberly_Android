package com.superbeta.blibberly_auth.presentation.ui.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.NoCredentialException
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.superbeta.blibberly_auth.BuildConfig
import com.superbeta.blibberly_auth.theme.components.PrimaryButton
import io.github.jan.supabase.exceptions.RestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.security.MessageDigest
import java.util.UUID


@Composable
fun GoogleSignInButton(buttonText: String, onGetCredentialResponse: (Credential) -> Unit) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    // Add this to check if Google Play Services is available
    var isGooglePlayServicesAvailable by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val googleApiAvailability = GoogleApiAvailability.getInstance()
            val resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context)
            isGooglePlayServicesAvailable = resultCode == ConnectionResult.SUCCESS
            Log.d("GoogleSignIn", "Google Play Services available: $isGooglePlayServicesAvailable")
        } catch (e: Exception) {
            Log.e("GoogleSignIn", "Error checking Google Play Services", e)
        }
    }

    PrimaryButton(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        buttonText = buttonText,
        isButtonEnabled = isGooglePlayServicesAvailable,
        onClickMethod = {
            scope.launch {
                try {
                    Log.d("GoogleSignIn", "Starting sign-in process")
                    Log.d("GoogleSignIn", "Using client ID: ${BuildConfig.WEB_GOOGLE_CLIENT_ID}")

                    // Most basic configuration possible
                    val googleIdOption = GetGoogleIdOption.Builder()
                        .setServerClientId(BuildConfig.WEB_GOOGLE_CLIENT_ID)
                        .setFilterByAuthorizedAccounts(false)
                        .setAutoSelectEnabled(false)
                        .build()

                    Log.d("GoogleSignIn", "Created GoogleIdOption")

                    val request = GetCredentialRequest.Builder()
                        .addCredentialOption(googleIdOption)
                        .build()

                    Log.d("GoogleSignIn", "Created GetCredentialRequest")

                    withContext(Dispatchers.IO) {
                        Log.d("GoogleSignIn", "Attempting to get credential")
                        val result = credentialManager.getCredential(
                            request = request,
                            context = context
                        )
                        Log.d("GoogleSignIn", "Successfully got credential")
                        withContext(Dispatchers.Main) {
                            onGetCredentialResponse(result.credential)
                        }
                    }

                } catch (e: NoCredentialException) {
                    Log.e("GoogleSignIn", "No credentials available", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Please ensure you have a Google account added to your device",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.e("GoogleSignIn", "Error during sign in", e)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            context,
                            "Sign in failed: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    )
}