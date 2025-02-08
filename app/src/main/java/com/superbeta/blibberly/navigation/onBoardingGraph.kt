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
import com.superbeta.blibberly.onBoarding.presentation.ui.InitialLoadingScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.OnBoardingScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.QueueScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.SkillsAndInterestsScreen
import com.superbeta.blibberly.utils.Routes
import com.superbeta.blibberly.utils.Screen

fun NavGraphBuilder.onBoardingGraph(navController: NavHostController, modifier: Modifier) {
    navigation(startDestination = Screen.InitialLoading.route, route = Routes.OnBoarding.graph) {
        composable(Screen.OnBoarding.route) {
            OnBoardingScreen(
                modifier,
                navigateBack = { navController.popBackStack() },
                navigateToAboutMe = {
                    navController.navigate(Screen.AboutMe.route)
                })
        }

        composable(Screen.AboutMe.route) {
            AboutMeScreen(modifier, navController)
        }
        composable(Screen.Bio.route) {
            BioScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                navigateToAboutMe = {
                    navController.navigate(Screen.AboutMe.route)
                })
        }

        composable(Screen.SkillsAndInterests.route) {
            SkillsAndInterestsScreen(
                modifier = modifier,
                navigateBack = { navController.popBackStack() },
                navigateToPhoto = {
                    navController.navigate(Screen.Photo.route)
                })
        }

        composable(Screen.Photo.route) {
            BlibmojiScreen(
                modifier,
                navigateBack = { navController.popBackStack() },
                navigateToCurateProfile = {
                    navController.navigate(Screen.InitialLoading.route)
                })
        }

        composable(Screen.InitialLoading.route) {
            InitialLoadingScreen(modifier, navigateToHome = {
                navController.navigate(Screen.Home.route)
            })
        }

        composable(Screen.Queue.route) {
            QueueScreen(modifier)
        }

        composable(Screen.NotificationConsent.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationConsentScreen(
                    modifier,
//                    navController
                )
            }
        }
    }
}