package com.superbeta.blibberly_auth

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.superbeta.blibberly_auth.navigation.AuthNavHost
import com.superbeta.blibberly_auth.ui.theme.AuthTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AuthTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
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

                    AuthNavHost(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

