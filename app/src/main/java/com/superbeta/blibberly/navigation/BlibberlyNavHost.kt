package com.superbeta.blibberly.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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
    navController: NavHostController, modifier: Modifier, startDestination: String, route: String
) {
    NavHost(navController = navController, startDestination = startDestination) {

        authNavGraph(navController, modifier)
        onBoardingGraph(navController, modifier)
        chatGraph(navController,modifier)

        composable(Screen.NotificationConsent.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationConsentScreen(
                    modifier,
//                    navController
                )
            }
        }

        composable("notification_sample") {
            NotificationSampleScreen()
        }

        composable(Screen.Notification.route) {
            NotificationScreen(modifier, navigateBack = { navController.popBackStack() })
        }

        composable(
            route = Screen.UserProfile.route + "/{userEmail}/{userName}", arguments = listOf(
                navArgument(name = "userEmail", builder = { type = NavType.StringType }),
                navArgument(name = "userName", builder = { type = NavType.StringType })
            )
        ) {
            UserProfileScreen(userEmail = it.arguments?.getString("userEmail")!!,
                userName = it.arguments?.getString("userName")!!,
                navigateToMessageScreen = {
                    navController.navigate(
                        Screen.Message.route + "/${it.arguments?.getString("userEmail")}/${
                            it.arguments?.getString(
                                "userName"
                            )
                        }"
                    )
                },
                navigateBack = { navController.popBackStack() })
        }



        composable(Screen.Home.route) {
            HomeScreen(modifier, navigateToChat = { email, name ->
                navController.navigate(Screen.Message.route + "/$email/$name")
            })
        }

        composable(Screen.Filter.route) {
            FilterScreen(modifier)
        }

        composable(Screen.CurrUserProfile.route) {
            CurrUserProfileScreen(modifier)
        }
    }
}




