package com.superbeta.blibberly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.superbeta.blibberly.auth.login.LoginScreen
import com.superbeta.blibberly.auth.register.RegisterScreen

@Composable
fun BlibberlyNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = "home"
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen()
        }

        composable("register") {
            RegisterScreen()
        }
    }
}



