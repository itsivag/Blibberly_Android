package com.superbeta.blibberly_auth.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.credentials.CredentialManager
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.theme.components.PrimaryButton
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun GoogleSignInButton(
//    navController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val credentialManager = CredentialManager.create(context)

    PrimaryButton(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        buttonText = "Sign in with Google",
        isButtonEnabled = true,
        onClickMethod = {
            scope.launch {
                authViewModel.signInWithGoogle()
//                AuthRepositoryImpl().signInWithGoogle(
//                    credentialManager,
//                    coroutineScope,
//                    context,
//                    { navController.navigate(Screen.Home.route) },
//                    { navController.navigate(Screen.OnBoarding.route) })
//                    {}, {})
            }

            scope.launch {
//                authViewModel.retrieveSession().collect { email ->
//                    Log.i("User Email From Data Store", email ?: "No email found")
//
//                }
            }

        })
}
