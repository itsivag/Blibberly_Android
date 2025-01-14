package com.superbeta.blibberly

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superbeta.blibberly.root.BlibberlyBottomBar
import com.superbeta.blibberly.root.BlibberlyTopAppBar
import com.superbeta.blibberly.navigation.BlibberlyNavHost
import com.superbeta.blibberly.notification.NotificationUtil
import com.superbeta.blibberly.ui.BlibberlyTheme
import com.superbeta.blibberly.utils.Routes
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.utils.AuthState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.compose.KoinContext


class MainActivity : ComponentActivity() {

    private val bottomNavScreens = listOf(
        Screen.CurrUserProfile,
        Screen.Home,
        Screen.ChatList,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        var keepSplashScreen = true
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            delay(1000)
            keepSplashScreen = false
        }
        enableEdgeToEdge()
//notification permission
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        val settingsIntent: Intent =
                            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                        startActivity(settingsIntent)
                    }
                }
            }

        NotificationUtil(
            activity = this,
            requestPermissionLauncher = requestPermissionLauncher
        ).askNotificationPermission()


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

                        Scaffold(topBar = {
                            if (isTopBarVisible) {
                                BlibberlyTopAppBar()
                            }
                        }, bottomBar = {
                            if (isBottomNavBarVisible) {
                                BlibberlyBottomBar(navController, bottomNavScreens, selectedScreen)
                            }

                        }) {
                            val authState = authViewModel.authState.collectAsState()
//                            var route = ""
                            var startNavRoute = ""
                            when (authState.value) {
                                AuthState.SIGNED_IN -> {
                                    startNavRoute = Screen.Home.route
//                                    route = ""
                                }
//                            AuthState.SIGNED_OUT -> Screen.SignIn.route
//                            AuthState.USER_EMAIL_STORED -> {}
//                            AuthState.USER_EMAIL_STORAGE_ERROR ->
                                AuthState.USER_NOT_REGISTERED -> {
                                    startNavRoute = Routes.OnBoarding.graph
//                                    route = Routes.OnBoarding.graph
                                }
//                            AuthState.ERROR ->
//                            AuthState.LOADING ->
//                            AuthState.IDLE ->
                                AuthState.USER_REGISTERED -> {
                                    startNavRoute = Screen.Home.route
//                                    route = ""
                                }

                                else -> {
                                    startNavRoute = Routes.Auth.graph
//                                    route = Routes.Auth.graph
                                }
                            }

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


    override fun onDestroy() {
        super.onDestroy()
//        socket.disconnectSocket()
    }
}

