package com.superbeta.blibberly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.superbeta.blibberly.auth.OTPScreen
import com.superbeta.blibberly.auth.presentation.SignInScreen
import com.superbeta.blibberly.auth.presentation.SignUpScreen
import com.superbeta.blibberly.home.main.presentation.ui.HomeScreen
import com.superbeta.blibberly.home.filter.FilterScreen
import com.superbeta.blibberly.home.notifications.NotificationScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.AboutMeScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BioScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.CurateProfilesScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.OnBoardingScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BlibmojiScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.SkillsAndInterestsScreen
import com.superbeta.blibberly.profile.ProfileScreen
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_chat.presentation.ui.MessageScreen
import com.superbeta.blibberly_chat.presentation.ui.components.ChatListScreen

@Composable
fun BlibberlyNavHost(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = Screen.Profile.route
) {

    NavHost(navController = navController, startDestination = startDestination) {
        composable(Screen.SignIn.route) {
            SignInScreen(modifier, navController)
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(modifier = modifier, navController = navController)
        }

        composable(Screen.OTPEnter.route) {
            OTPScreen(modifier, navController)
        }
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(modifier, navController)
        }

        composable(Screen.AboutMe.route) {
            AboutMeScreen(modifier, navController)
        }
        composable(Screen.Bio.route) {
            BioScreen(modifier = modifier, navController = navController)
        }

        composable(Screen.SkillsAndInterests.route) {
            SkillsAndInterestsScreen(modifier = modifier, navController = navController)
        }

        composable(Screen.Photo.route) {
            BlibmojiScreen(modifier, navController)
        }

        composable(Screen.CurateProfile.route) {
            CurateProfilesScreen(modifier, navController)
        }
        composable(Screen.Notification.route) {
            NotificationScreen(modifier, navController)
        }

        composable(Screen.Home.route) {
            HomeScreen(modifier, navController)
        }
        composable(Screen.ChatList.route) {
            ChatListScreen(modifier, navController)
        }

        composable(Screen.Message.route) {
            MessageScreen(modifier, navController)
        }
        composable(Screen.Filter.route) {
            FilterScreen(modifier, navController)
        }

        composable(Screen.Profile.route) {
            ProfileScreen(modifier, navController)
        }
    }
}




