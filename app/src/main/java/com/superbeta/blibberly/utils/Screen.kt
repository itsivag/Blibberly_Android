package com.superbeta.blibberly.utils

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Register : Screen("register")
    data object OTPEnter : Screen("otp_enter")
    data object SkillsAndInterests : Screen("skill_and_interests")
    data object Bio : Screen("bio")
    data object Profile : Screen("profile")
    data object ChatList : Screen("chat_list")
    data object Message : Screen("messages")
    data object Home : Screen("home")
    data object Notification : Screen("notifications")
    data object Filter : Screen("filters")
    data object OnBoarding : Screen("on_boarding")
    data object Photo : Screen("photo")
    data object AboutMe : Screen("about_me")
    data object CurateProfile : Screen("curate_Profiles")
}