package com.superbeta.blibberly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.superbeta.blibberly.auth.login.LoginScreen
import com.superbeta.blibberly.auth.register.RegisterScreen
import com.superbeta.blibberly.chat.ChatScreen
import com.superbeta.blibberly.home.HomeScreen

@Composable
fun BlibberlyNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = "home"
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(modifier)
        }

        composable("register") {
            RegisterScreen(modifier)
        }

        composable("home") {
            HomeScreen(modifier,navController)
        }
        composable("chat") {
            ChatScreen(modifier)
        }
    }
}




