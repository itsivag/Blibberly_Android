package com.superbeta.blibberly.navigation

import android.os.Build
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.superbeta.blibberly.notification.NotificationConsentScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.AboutMeScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BioScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.BlibmojiScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.CurateProfileScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.OnBoardingScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.QueueScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.InterestsScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.JobAndLanguageScreen
import com.superbeta.blibberly.utils.Routes
import com.superbeta.blibberly.utils.Screen

fun NavGraphBuilder.onBoardingGraph(navController: NavHostController, modifier: Modifier) {
    navigation(
        startDestination = Screen.Bio.route,
        route = Routes.OnBoarding.graph
    ) {
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(
                modifier,
                navigateBack = { navController.popBackStack() },
                navigateToAboutMe = {
                    navController.navigate(Screen.AboutMe.route)
                })
        }

        composable(Screen.Bio.route) {
            BioScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                navigateToAboutMe = {
                    navController.navigate(Screen.AboutMe.route)
                })
        }

        composable(Screen.AboutMe.route) {
            AboutMeScreen(modifier, navController)
        }

        composable(Screen.Interests.route) {
            InterestsScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                navigateToJobAndLanguage = {
                    navController.navigate(Screen.JobAndLanguage.route)
                })
        }

        composable(Screen.JobAndLanguage.route) {
            JobAndLanguageScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                navigateToPhoto = { navController.navigate(Screen.Blibmoji.route) })
        }

        composable(Screen.Blibmoji.route) {
            BlibmojiScreen(
                modifier,
                navigateBack = { navController.popBackStack() },
                navigateToNotificationConsent = {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        navController.navigate(Screen.NotificationConsent.route)
                    else
                        navController.navigate(Screen.Queue.route)
                })
        }

        composable(Screen.CurateProfiles.route) {
            CurateProfileScreen(modifier, navigateToHome = {
                navController.navigate(Screen.Home.route)
            })
        }

        composable(Screen.Queue.route) {
            QueueScreen(
                modifier,
                navigateToInitialLoadingScreen = { navController.navigate(Screen.CurateProfiles.route) })
        }

        composable(Screen.NotificationConsent.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationConsentScreen(
                    modifier,
                    navigateToQueueScreen = {
                        navController.navigate(Screen.Queue.route)
                    }
                )
            }
        }
    }
}