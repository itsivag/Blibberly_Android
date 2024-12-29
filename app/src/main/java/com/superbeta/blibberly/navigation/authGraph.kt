package com.superbeta.blibberly.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.superbeta.blibberly.auth.presentation.ui.SignUpScreen
import com.superbeta.blibberly.utils.Routes
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.presentation.ui.OTPScreen
import com.superbeta.blibberly_auth.presentation.ui.SignInScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController, modifier: Modifier) {
    navigation(startDestination = Screen.SignIn.route, route = Routes.Auth.graph) {

        composable(Screen.SignIn.route) {
            SignInScreen(modifier, navigateToHome = {
                navController.navigate(Screen.Home.route)

            }, navigateToSignUp = {
                navController.navigate(Screen.SignUp.route)
            })
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(modifier = modifier, navController = navController)
        }

        composable(Screen.OTPEnter.route) {
            OTPScreen(
                modifier,
                navigateBack = { navController.popBackStack() },
                navigateToOnBoarding = { navController.navigate(Screen.OnBoarding.route) })
        }

    }
}