package com.superbeta.blibberly_home.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.utils.Routes
import com.superbeta.blibberly_auth.utils.Screen
import com.superbeta.blibberly_home.presentation.ui.HomeScreen
import com.superbeta.blibberly_home.presentation.ui.NoUsersScreen
import com.superbeta.blibberly_home.presentation.ui.UserProfileScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController,
    modifier: Modifier,
    startDestination: String = Screen.Home.route,
//    signInWithGoogle: () -> Unit
) {
    navigation(startDestination = startDestination, route = Routes.Home.graph) {
        composable(
            route = Screen.UserProfile.route + "/{userEmail}/{userName}", arguments = listOf(
                navArgument(name = "userEmail", builder = { type = NavType.StringType }),
                navArgument(name = "userName", builder = { type = NavType.StringType })
            )
        ) {
            UserProfileScreen(userEmail = it.arguments?.getString(
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
            HomeScreen(
                modifier,
                navigateToChat = { email, name ->
                    navController.navigate(Screen.Message.route + "/$email/$name")
                },
                navigateToNoUsers = { navController.navigate(Screen.NoUsers.route) })
        }

        composable(Screen.NoUsers.route) {
            NoUsersScreen(modifier)
        }
    }

}

@Composable
fun SignInScreen(
    modifier: Modifier,
    viewModel: AuthViewModel = koinViewModel(),
//    signInWithGoogle: () -> Unit
) {
    Column(modifier = modifier.fillMaxSize()) {
        Button(onClick = {
//            signInWithGoogle()
        }) {
            Text(text = "Sign In With Google")
        }
    }
}
