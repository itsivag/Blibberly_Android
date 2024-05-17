package com.superbeta.blibberly.utils

sealed class Screen(val route: String) {
    data object Profile : Screen("profile")
    data object Chat : Screen("chat")
    data object Home : Screen("home")
    data object Notification : Screen("notifications")
    data object Filter : Screen("filters")
    data object OnBoarding : Screen("on_boarding")
    data object Photo : Screen("photo")
}