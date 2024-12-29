package com.superbeta.blibberly.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.superbeta.blibberly.utils.Routes
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_chat.presentation.ui.ChatListScreen
import com.superbeta.blibberly_chat.presentation.ui.MessageScreen

fun NavGraphBuilder.chatGraph(navController: NavHostController, modifier: Modifier) {
    navigation(startDestination = Screen.ChatList.route, route = Routes.Chat.graph) {

        composable(Screen.ChatList.route) {
            ChatListScreen(modifier, navigateToMessage = { userEmail, userName ->
                navController.navigate(
                    Screen.Message.route + "/$userEmail/$userName"
                )
            })
        }

        composable(
            Screen.Message.route + "/{userEmail}/{userName}",
            arguments = listOf(
                navArgument("userEmail", builder = { type = NavType.StringType }),
                navArgument("userName", builder = { type = NavType.StringType })
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getString("userEmail")?.let { argUserEmail ->
                backStackEntry.arguments?.getString("userName")?.let { argUserName ->
                    MessageScreen(
                        navigateToProfile = { userEmail, userName ->
                            navController.navigate(
                                Screen.UserProfile.route + "/$userEmail/$userName"
                            )
                        },
                        navigateBack = { navController.popBackStack() },
                        receiverUserEmail = argUserEmail,
                        receiverUserName = argUserName,
                    )
                }
            }
        }

    }
}