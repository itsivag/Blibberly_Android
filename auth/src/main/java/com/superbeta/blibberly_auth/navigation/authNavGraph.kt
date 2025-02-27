package com.superbeta.blibberly_auth.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.superbeta.blibberly_auth.presentation.ui.UserFlowScreen
import com.superbeta.blibberly_auth.utils.Routes
import com.superbeta.blibberly_auth.utils.Screen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = Screen.UserFlow.route
) {
//    NavHost(navController, Routes.Auth.graph) {
    navigation(startDestination = startDestination, route = Routes.Auth.graph) {

//            composable(Screen.SignIn.route) {
//                SignInScreen(modifier,
//                navigateToHome = {
//                navController.navigate(Screen.Home.route)
//            }
//                ,
//                    navigateToSignUp = {
//                        navController.navigate(Screen.SignUp.route)
//                    })
//            }
//
//            composable(Screen.SignUp.route) {
//                SignUpScreen(modifier = modifier, navigateBack = { navController.popBackStack() })
//            }

//            composable(Screen.OTPEnter.route) {
//                OTPScreen(
//                    modifier,
//                    navigateBack = { navController.popBackStack() },
//                    navigateToOnBoarding = { navController.navigate(Screen.OnBoarding.route) })
//            }
//
        composable(Screen.UserFlow.route) {
            UserFlowScreen(modifier)
        }
    }

//    }
}