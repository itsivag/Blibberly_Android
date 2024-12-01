package com.superbeta.blibberly.utils

sealed class Screen(val route: String) {
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object OTPEnter : Screen("otp_enter")
    data object SkillsAndInterests : Screen("skill_and_interests")
    data object Bio : Screen("bio")
    data object CurrUserProfile : Screen("profile")
    data object ChatList : Screen("chat_list")
    data object Message : Screen("messages")
    data object Home : Screen("home")
    data object NotificationConsent : Screen("notification_consent")
//    data object Filter : Screen("filters")
    data object OnBoarding : Screen("on_boarding")
    data object Photo : Screen("photo")
    data object AboutMe : Screen("about_me")
    data object CurateProfile : Screen("curate_Profiles")
    data object UserProfile : Screen("user_profile")
    data object NoUsers : Screen("no_users")
}