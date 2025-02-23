package com.superbeta.blibberly

import android.content.Intent
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superbeta.blibberly.root.BlibberlyTopAppBar
import com.superbeta.blibberly.navigation.BlibberlyNavHost
import com.superbeta.blibberly.ui.BlibberlyTheme
import com.superbeta.blibberly.utils.Routes
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.AuthActivity
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.utils.AuthState
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.compose.KoinContext


class MainActivity : ComponentActivity() {

    private val bottomNavScreens = listOf(
        Screen.CurrUserProfile,
        Screen.Home,
        Screen.ChatList,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(this,AuthActivity::class.java))
////notification permission
//        val requestPermissionLauncher =
//            registerForActivityResult(
//                ActivityResultContracts.RequestPermission()
//            ) { isGranted: Boolean ->
//                if (isGranted) {
//                    Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
//                        .show()
//                } else {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                        val settingsIntent: Intent =
//                            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
//                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
//                        startActivity(settingsIntent)
//                    }
//                }
//            }

//        NotificationUtil(
//            activity = this,
//            requestPermissionLauncher = requestPermissionLauncher
//        ).askNotificationPermission()


        var isTopBarVisible by mutableStateOf(false)
        var isBottomNavBarVisible by mutableStateOf(false)

        setContent {
            KoinContext {
                BlibberlyTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {

                        val authViewModel = getViewModel<AuthViewModel>()

                        val scope = rememberCoroutineScope()
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

                        val authState by authViewModel.authState.collectAsStateWithLifecycle()
                        var startNavRoute by remember {
                            mutableStateOf(Routes.OnBoarding.graph)
                        }

                        LaunchedEffect(authState) {
                            Log.i("AUTH STATE --->>", authState.toString())
                            startNavRoute = when (authState) {
                                AuthState.SIGNED_IN -> {
                                    Screen.Home.route
                                }

                                AuthState.SIGNED_UP -> {
                                    Routes.OnBoarding.graph
                                }

                                else -> {
                                    Routes.OnBoarding.graph
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
//                                startDestination = Routes.OnBoarding.graph,
                                route = ""
                            )
                        }
                    }
                }
            }

        }
    }


    override fun onDestroy() {
        super.onDestroy()
//        socket.disconnectSocket()
    }
}

