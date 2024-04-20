package com.superbeta.blibberly.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.superbeta.blibberly.utils.SupabaseInstance.supabase
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.launch

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreen() {
    var userEmail by remember {
        mutableStateOf(
            TextFieldValue()
        )
    }

    var userPassword by remember {
        mutableStateOf(
            TextFieldValue()
        )
    }


    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Login")

        Text(text = "Email")
        OutlinedTextField(value = userEmail, onValueChange = {
            userEmail = it
        }, prefix = { Icon(imageVector = Icons.Default.MailOutline, contentDescription = "Email") })

        Text(text = "Password")
        OutlinedTextField(value = userPassword, onValueChange = {
            userPassword = it
        }, prefix = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Password") })

        val c = rememberCoroutineScope()
        Button(
            onClick = {
                // Launch a coroutine to call the signInWithEmail function
                c.launch {
                    signInWithEmail(userEmail.text, userPassword.text)
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Text(text = "Sign in")
        }

        Divider()

        Button(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black
            )
        ) {
            Row {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Sign in with Google"
                )
                Text(text = "Sign in with Google")
            }
        }

    }
}

suspend fun signInWithEmail(userEmail: String, userPassword: String) {
    val user = supabase.auth.signUpWith(Email) {
        email = userEmail
        password = userPassword
    }
}
