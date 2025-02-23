package com.superbeta.blibberly.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.superbeta.blibberly.onBoarding.presentation.ui.CurateProfileScreen
import com.superbeta.blibberly.onBoarding.presentation.ui.InitialLoading
import com.superbeta.blibberly.profile.CurrUserProfileScreen
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.navigation.authNavGraph

@Composable
fun BlibberlyNavHost(
    navController: NavHostController, modifier: Modifier, startDestination: String, route: String
) {
    NavHost(navController = navController, startDestination = startDestination) {
        authNavGraph(navController, modifier)
        onBoardingGraph(navController, modifier)
        chatGraph(navController, modifier)

        composable(Screen.InitialLoading.route) {
            InitialLoading(modifier = modifier)
        }

        composable(
            route = Screen.UserProfile.route + "/{userEmail}/{userName}", arguments = listOf(
                navArgument(name = "userEmail", builder = { type = NavType.StringType }),
                navArgument(name = "userName", builder = { type = NavType.StringType })
            )
        ) {
            com.superbeta.blibberly_home.presentation.ui.UserProfileScreen(userEmail = it.arguments?.getString(
                "userEmail"
            )!!,
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
            com.superbeta.blibberly_home.presentation.ui.HomeScreen(
                modifier,
                navigateToChat = { email, name ->
                    navController.navigate(Screen.Message.route + "/$email/$name")
                },
                navigateToNoUsers = { navController.navigate(Screen.NoUsers.route) })
        }

//        composable(Screen.Filter.route) {
//            FilterScreen(modifier)
//        }

        composable(Screen.CurrUserProfile.route) {
            CurrUserProfileScreen(modifier)
        }

        composable(Screen.NoUsers.route) {
            com.superbeta.blibberly_home.presentation.ui.NoUsersScreen(modifier)
        }
    }
}




