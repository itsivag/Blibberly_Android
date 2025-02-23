package com.superbeta.blibberly_auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.superbeta.blibberly_auth.ui.theme.AuthTheme

class AuthActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}

