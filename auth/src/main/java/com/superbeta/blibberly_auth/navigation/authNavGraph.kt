package com.superbeta.blibberly_auth.navigation

import android.app.Activity
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.superbeta.blibberly_auth.presentation.ui.UserFlowScreen
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.utils.Routes
import com.superbeta.blibberly_auth.utils.Screen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = Screen.SignIn.route,
//    signInWithGoogle: () -> Unit
) {
    navigation(startDestination = startDestination, route = Routes.Auth.graph) {
        composable(Screen.SignIn.route) {
            SignInScreen(modifier)
        }
        composable(Screen.UserFlow.route) {
            UserFlowScreen(modifier)
        }
    }

}

@Composable
fun SignInScreen(
    modifier: Modifier,
    viewModel: AuthViewModel = koinViewModel(),
//    signInWithGoogle: () -> Unit
) {
    val context = LocalContext.current

    // Get the Activity reference
    val activity = remember(context) {
        context as? Activity ?: (context as? ContextWrapper)?.baseContext as? Activity
    }
    Column(modifier = modifier.fillMaxSize()) {
        Button(onClick = {
            if (activity != null) {
                viewModel.signInWithGoogle(activity)
            } else {
                Log.e("SignInScreen", "Cannot get Activity reference")
            }
        }) {
            Text(text = "Sign In With Google")
        }
    }
}
