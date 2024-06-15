package com.superbeta.blibberly.auth.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.navigation.NavHostController
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.superbeta.blibberly.R
import com.superbeta.blibberly.auth.AuthRepositoryImpl
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.components.PrimaryButton
import com.superbeta.blibberly.utils.supabase
import io.github.jan.supabase.exceptions.RestException
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.Google
import io.github.jan.supabase.gotrue.providers.builtin.IDToken
import kotlinx.coroutines.launch
import java.security.MessageDigest
import java.util.UUID

@Composable
fun LoginScreen(modifier: Modifier, navController: NavHostController) {


    var email by remember {
        mutableStateOf(TextFieldValue())
    }

    var password by remember {
        mutableStateOf(TextFieldValue())
    }

    var isButtonEnabled by remember {
        mutableStateOf(true)
    }

//    isButtonEnabled = phoneNumber.text.length == 10

    val scope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val context = LocalContext.current
        val internalModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)


        Text(text = "Let's sign you in", style = MaterialTheme.typography.titleLarge)
        Text(text = "Welcome back.")
        Text(text = "You've been missed!")
        Spacer(modifier = Modifier.weight(1f))

        OutlinedTextField(
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email, contentDescription = "Email"
                )
            },
            value = email,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            onValueChange = {
                email = it
            },
            modifier = internalModifier,
            shape = RoundedCornerShape(14.dp),
            label = { Text(text = "Email") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ColorPrimary,
                unfocusedBorderColor = ColorDisabled,
            )
        )

        OutlinedTextField(
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock, contentDescription = "Password"
                )
            },
            value = password,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = {
                password = it
            },
            modifier = internalModifier,
            shape = RoundedCornerShape(14.dp),
            label = { Text(text = "Password") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ColorPrimary,
                unfocusedBorderColor = ColorDisabled,
            )
        )

        PrimaryButton(modifier = internalModifier,
            buttonText = "Sign In",
            isButtonEnabled = isButtonEnabled,
            onClickMethod = {
                scope.launch {
                    AuthRepositoryImpl().createUser(
                        mEmail = email.text, mPassword = password.text
                    )

                }
//                navController.navigate("otp_enter")
            })

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Or",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            HorizontalDivider(
                modifier = Modifier.weight(1f)
            )
        }

        GoogleSignInButton()

    }
}

@Composable
fun GoogleSignInButton() {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val onClick: () -> Unit = {
        val credentialManager = CredentialManager.create(context)

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

        coroutineScope.launch {
            try {
                val result = credentialManager.getCredential(
                    request = request,
                    context = context,
                )

                val googleIdTokenCredential =
                    GoogleIdTokenCredential.createFrom(result.credential.data)

                val googleIdToken = googleIdTokenCredential.idToken

                supabase.auth.signInWith(IDToken) {
                    idToken = googleIdToken
                    provider = Google
                    nonce = rawNonce
                }

                Log.i("Google Sign In", "Sign In Successful")

                // Handle successful sign-in
//            } catch (e: GetCredentialException) {
//                e.printStackTrace()
                // Handle GetCredentialException thrown by `credentialManager.getCredential()`
            } catch (e: GoogleIdTokenParsingException) {
                // Handle GoogleIdTokenParsingException thrown by `GoogleIdTokenCredential.createFrom()`
                e.printStackTrace()
            } catch (e: RestException) {
                // Handle RestException thrown by Supabase
                e.printStackTrace()
            } catch (e: Exception) {
                // Handle unknown exceptions
                e.printStackTrace()
            }
        }
    }

    PrimaryButton(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        buttonText = "Sign in with Google",
        isButtonEnabled = true,
        onClickMethod = { onClick() })
}
