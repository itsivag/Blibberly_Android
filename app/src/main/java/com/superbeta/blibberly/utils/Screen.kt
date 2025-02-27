package com.superbeta.blibberly.utils

sealed class Screen(val route: String) {
    data object InitialLoading : Screen("initial_loading")
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object OTPEnter : Screen("otp_enter")
    data object Interests : Screen("skill_and_interests")
    data object Bio : Screen("bio")
    data object CurrUserProfile : Screen("profile")
    data object ChatList : Screen("chat_list")
    data object Message : Screen("messages")
    data object Home : Screen("home")
    data object NotificationConsent : Screen("notification_consent")
    data object Queue : Screen("queue")

    //    data object Filter : Screen("filters")
    data object OnBoarding : Screen("on_boarding")
    data object Blibmoji : Screen("photo")
    data object JobAndLanguage : Screen("job_and_language")
    data object AboutMe : Screen("about_me")
    data object CurateProfiles : Screen("curate_profiles")
    data object UserProfile : Screen("user_profile")
    data object NoUsers : Screen("no_users")
}

sealed class Routes(val graph: String) {
    data object Auth : Routes("auth")
    data object OnBoarding : Routes("onboarding")
    data object Chat : Routes("chat")
}