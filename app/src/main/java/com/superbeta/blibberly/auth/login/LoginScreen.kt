package com.superbeta.blibberly.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import androidx.navigation.NavHostController
import com.superbeta.blibberly.auth.AuthRepositoryImpl
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.components.PrimaryButton
import com.superbeta.blibberly.utils.Screen
import kotlinx.coroutines.launch

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

        GoogleSignInButton(navController)

    }
}

@Composable
fun GoogleSignInButton(navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    PrimaryButton(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        buttonText = "Sign in with Google",
        isButtonEnabled = true,
        onClickMethod = {
            coroutineScope.launch {
                AuthRepositoryImpl().signInWithGoogle(
                    credentialManager,
                    coroutineScope,
                    context,
                    { navController.navigate(Screen.Home.route) },
                    { navController.navigate(Screen.OnBoarding.route) })
            }
        }
    )
}
