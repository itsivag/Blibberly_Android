package com.superbeta.blibberly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.superbeta.blibberly.onboarding.InitialLoading
import com.superbeta.blibberly.profile.CurrUserProfileScreen
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.navigation.authNavGraph
import com.superbeta.blibberly_home.navigation.homeNavGraph

@Composable
fun BlibberlyNavHost(
    navController: NavHostController, modifier: Modifier, startDestination: String, route: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        authNavGraph(navController, modifier = modifier)
        onBoardingGraph(navController, modifier)
        homeNavGraph(navController, modifier)
        chatGraph(navController, modifier)

        composable(Screen.InitialLoading.route) {
            InitialLoading(modifier = modifier)
        }


//        composable(Screen.Filter.route) {
//            FilterScreen(modifier)
//        }

        composable(Screen.CurrUserProfile.route) {
            CurrUserProfileScreen(modifier)
        }


    }
}




