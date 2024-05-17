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
import com.superbeta.blibberly.home.filter.FilterScreen
import com.superbeta.blibberly.home.notifications.NotificationScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.AboutMeScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BioScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.OnBoardingScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.SkillsAndInterestsScreen
import com.superbeta.blibberly.profile.ProfileScreen
import com.superbeta.blibberly.utils.Screen

@Composable
fun BlibberlyNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = Screen.Profile.route
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginScreen(modifier, navController)
        }

        composable("register") {
            RegisterScreen(modifier)
        }

        composable("otp_enter") {
            OTPScreen(modifier, navController)
        }
        composable(Screen.OnBoarding.route) {
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

        composable(Screen.Notification.route) {
            NotificationScreen(modifier, navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(modifier, navController)
        }
        composable(Screen.Chat.route) {
            ChatScreen(modifier, navController)
        }
        composable(Screen.Filter.route) {
            FilterScreen(modifier, navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(modifier, navController)
        }
    }
}




