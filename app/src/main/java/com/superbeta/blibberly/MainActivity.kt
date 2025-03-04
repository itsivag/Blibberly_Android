package com.superbeta.blibberly

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.credentials.CredentialManager
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.superbeta.blibberly.navigation.BlibberlyNavHost
import com.superbeta.blibberly.root.BlibberlyTopAppBar
import com.superbeta.blibberly.ui.BlibberlyTheme
import com.superbeta.blibberly.utils.Routes
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.presentation.viewmodel.UserInfoState
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authViewModel = getViewModel<AuthViewModel>()

        auth = Firebase.auth
        credentialManager = CredentialManager.create(baseContext)

        var isTopBarVisible by mutableStateOf(false)
        var isBottomNavBarVisible by mutableStateOf(false)

        setContent {
            KoinContext {
                BlibberlyTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        val navController = rememberNavController()
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        var selectedScreen by remember {
                            mutableStateOf(Screen.Home.route)
                        }

                        when (navBackStackEntry?.destination?.route) {
                            Screen.Home.route -> {
                                isTopBarVisible = true
                                isBottomNavBarVisible = true
                                selectedScreen = Screen.Home.route
                            }

                            Screen.CurrUserProfile.route -> {
                                isTopBarVisible = false
                                isBottomNavBarVisible = true
                                selectedScreen = Screen.CurrUserProfile.route

                            }

                            Screen.ChatList.route -> {
                                isTopBarVisible = false
                                isBottomNavBarVisible = true
                                selectedScreen = Screen.ChatList.route
                            }


                            else -> {
                                isTopBarVisible = false
                                isBottomNavBarVisible = false
                            }

                        }

                        var startNavRoute by remember {
                            mutableStateOf(Screen.InitialLoading.route)
                        }
                        val authState by authViewModel.userInfoState.collectAsStateWithLifecycle()
                        Log.i("MainActivity", authState.toString())
                        LaunchedEffect(authState) {
                            startNavRoute = when (authState) {
                                UserInfoState.Failed -> {
                                    Log.i(
                                        "MainActivity",
                                        "Failed to Sign In"
                                    )
                                    Routes.Auth.graph
                                }

                                is UserInfoState.Success -> {
                                    Log.i(
                                        "MainActivity",
                                        "Current User : ${(authState as? UserInfoState.Success)?.user?.email ?: "Unable to fetch"}"
                                    )

                                    Routes.Home.graph
                                }

                                UserInfoState.NotRegistered -> {
                                    Routes.OnBoarding.graph
                                }

                                null -> {
                                    Routes.Auth.graph
                                }

                            }
                        }

                        Scaffold(
                            topBar = {
                                if (isTopBarVisible) {
                                    BlibberlyTopAppBar(navigateToCurrUserProfile = {
                                        navController.navigate(
                                            Screen.CurrUserProfile.route
                                        )
                                    }, navigateToChatListScreen = {
                                        navController.navigate(
                                            Screen.ChatList.route
                                        )
                                    })
                                }
                            },
//                            bottomBar = {
//                            if (isBottomNavBarVisible) {
//                                BlibberlyBottomBar(navController, bottomNavScreens, selectedScreen)
//                            }
//
//                        }
                        ) {
                            BlibberlyNavHost(
                                navController = navController,
                                modifier = Modifier.padding(it),
                                startDestination = startNavRoute,
                                route = ""
                            )
                        }
                    }
                }
            }

        }
    }
}

