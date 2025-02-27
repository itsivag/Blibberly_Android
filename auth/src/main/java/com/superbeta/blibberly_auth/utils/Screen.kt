package com.superbeta.blibberly_auth.utils

import okhttp3.Route

sealed class Screen(val route: String) {
    data object SignIn : Screen("sign_in")
    data object SignUp : Screen("sign_up")
    data object OTPEnter : Screen("otp_enter")
    data object Message : Screen("messages")
    data object Home : Screen("home")

    //    data object Filter : Screen("filters")
    data object OnBoarding : Screen("on_boarding")
    data object Blibmoji : Screen("photo")
    data object JobAndLanguage : Screen("job_and_language")
    data object AboutMe : Screen("about_me")
    data object InitialLoading : Screen("initial_loading")
    data object UserProfile : Screen("user_profile")
    data object NoUsers : Screen("no_users")

//    data object UserFlow : Screen("user_flow")
}

sealed class Routes(val graph: String) {
    data object Auth : Routes("auth")
    data object OnBoarding : Routes("onboarding")

    data object Chat : Routes("chat")
    data object Home : Routes("home_graph")
}