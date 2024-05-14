package com.superbeta.blibberly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.superbeta.blibberly.auth.OTPScreen
import com.superbeta.blibberly.auth.login.LoginScreen
import com.superbeta.blibberly.auth.register.RegisterScreen
import com.superbeta.blibberly.chat.ChatScreen
import com.superbeta.blibberly.home.HomeScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.AboutMeScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BioScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.OnBoardingScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.SkillsAndInterestsScreen
import com.superbeta.blibberly.profile.ProfileScreen

@Composable
fun BlibberlyNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = "onboarding"
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(modifier, navController)
        }

        composable("register") {
            RegisterScreen(modifier)
        }

        composable("onboarding") {
            OnBoardingScreen(modifier, navController)
        }

        composable("about_me") {
            AboutMeScreen(modifier, navController)
        }
        composable("bio") {
            BioScreen(modifier = modifier, navController = navController)
        }

        composable("skill_and_interests") {
            SkillsAndInterestsScreen(modifier = modifier, navController = navController)
        }


        composable("home") {
            HomeScreen(modifier, navController)
        }
        composable("chat") {
            ChatScreen(modifier, navController)
        }

        composable("otp_enter") {
            OTPScreen(modifier, navController)
        }


        composable("profile") {
            ProfileScreen()
        }
    }
}




