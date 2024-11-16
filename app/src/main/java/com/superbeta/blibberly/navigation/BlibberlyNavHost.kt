package com.superbeta.blibberly.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.superbeta.blibberly.auth.presentation.ui.SignUpScreen
import com.superbeta.blibberly.home.main.presentation.ui.HomeScreen
import com.superbeta.blibberly.home.filter.FilterScreen
import com.superbeta.blibberly.home.main.presentation.ui.UserProfileScreen
import com.superbeta.blibberly.home.notifications.NotificationScreen
import com.superbeta.blibberly.notification.NotificationConsentScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.AboutMeScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BioScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.CurateProfilesScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.OnBoardingScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BlibmojiScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.SkillsAndInterestsScreen
import com.superbeta.blibberly.profile.CurrUserProfileScreen
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.presentation.ui.OTPScreen
import com.superbeta.blibberly_auth.presentation.ui.SignInScreen
import com.superbeta.blibberly_chat.notification.NotificationSampleScreen
import com.superbeta.blibberly_chat.presentation.ui.MessageScreen
import com.superbeta.blibberly_chat.presentation.ui.ChatListScreen

@Composable
fun BlibberlyNavHost(
    navController: NavHostController, modifier: Modifier, startDestination: String
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

        composable(Screen.NotificationConsent.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationConsentScreen(modifier, navController)
            }
        }

        composable("notification_sample") {
            NotificationSampleScreen()
        }

        composable(Screen.Notification.route) {
            NotificationScreen(modifier, navController)
        }

        composable(
            route = Screen.UserProfile.route + "/{userEmail}/{userName}", arguments = listOf(
                navArgument(name = "userEmail", builder = { type = NavType.StringType }),
                navArgument(name = "userName", builder = { type = NavType.StringType })
            )
        ) {
            UserProfileScreen(
                userEmail = it.arguments?.getString("userEmail")!!,
                userName = it.arguments?.getString("userName")!!,
                navigateToMessageScreen = {
                    navController.navigate(
                        Screen.Message.route + "/${it.arguments?.getString("userEmail")}/${
                            it.arguments?.getString(
                                "userName"
                            )
                        }"
                    )
                }, navigateBack = { navController.popBackStack() })
        }



        composable(Screen.Home.route) {
            HomeScreen(modifier, navController)
        }

        composable(Screen.ChatList.route) {
            ChatListScreen(modifier, navigateToMessage =   {userEmail,userName -> navController.navigate(
                Screen.Message.route + "/$userEmail/$userName"
            )})
        }

        composable(
            Screen.Message.route + "/{userEmail}/{userName}",
            arguments = listOf(
                navArgument("userEmail", builder = { type = NavType.StringType }),
                navArgument("userName", builder = { type = NavType.StringType })
            )
        ) { backStackEntry ->
            MessageScreen(
                modifier,
                navigateToProfile = {
                    navController.navigate(
                        Screen.UserProfile.route + "/${backStackEntry.arguments?.getString("userEmail")}/${
                            backStackEntry.arguments?.getString(
                                "userName"
                            )
                        }"
                    )
                },
                navigateBack = { navController.popBackStack() },
                backStackEntry.arguments?.getString("userEmail"),
                backStackEntry.arguments?.getString("userName"),
            )
        }
        composable(Screen.Filter.route) {
            FilterScreen(modifier, navController)
        }

        composable(Screen.CurrUserProfile.route) {
            CurrUserProfileScreen(modifier, navController)
        }
    }
}




