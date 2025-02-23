package com.superbeta.blibberly_auth

import android.content.Intent
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.callback.Callback
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.superbeta.blibberly_auth.navigation.AuthNavHost
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.presentation.viewmodel.UserInfoState
import com.superbeta.blibberly_auth.ui.theme.AuthTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

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
                    AuthNavHost(
                        navController = rememberNavController(),
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

