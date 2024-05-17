package com.superbeta.blibberly

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superbeta.blibberly.navigation.BlibberlyNavHost
import com.superbeta.blibberly.ui.theme.BlibberlyTheme
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.utils.Screen
import io.getstream.chat.android.client.ChatClient
import io.getstream.chat.android.client.logger.ChatLogLevel
import io.getstream.chat.android.models.User
import io.getstream.chat.android.offline.plugin.factory.StreamOfflinePluginFactory
import io.getstream.chat.android.state.plugin.config.StatePluginConfig
import io.getstream.chat.android.state.plugin.factory.StreamStatePluginFactory


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bottomNavScreens = listOf(
            Screen.Profile,
            Screen.Home,
            Screen.Chat,
        )
        //supabase
//        val supabase = SupabaseInstance.supabase
//        val auth = supabase.auth

        //stream
        val apiKey = "y2st43tkq85k"
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoiMSJ9.NACLyT7Yejq55wB-54JXh61efspeZ__jDVL2lUSDW2M"

        val offlinePluginFactory = StreamOfflinePluginFactory(applicationContext)
        val statePluginFactory = StreamStatePluginFactory(
            config = StatePluginConfig(
                backgroundSyncEnabled = true, userPresence = true
            ), appContext = applicationContext
        )

        val client = ChatClient.Builder(apiKey, applicationContext).logLevel(ChatLogLevel.ALL)
            .withPlugins(offlinePluginFactory, statePluginFactory).build()

        val user = User(
            id = "1",
            name = "Paranoid Android",
            image = "https://bit.ly/2TIt8NR",
        )

        client.connectUser(
            user = user,
            token = token, // or client.devToken(userId); if auth is disabled for your app
        ).enqueue { result ->
            if (result.isSuccess) {
                // Handle success
                Log.i("itsivag", "logged in")
            } else {
                // Handler error
                Log.i("itsivag", "error signing in")
            }
        }

        var isTopBarVisible by mutableStateOf(false)
        setContent {
            BlibberlyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    when (navBackStackEntry?.destination?.route) {
                        Screen.Home.route -> {
                            isTopBarVisible = true
                        }
                        else -> {
                            isTopBarVisible = false
                        }

                    }

                    var selectedScreen by remember {
                        mutableStateOf(Screen.Home.route)
                    }

                    Scaffold(topBar = {
                        if (isTopBarVisible) {
                            CenterAlignedTopAppBar(title = { Text(text = "Blibberly") },
                                navigationIcon = {
                                    IconButton(onClick = { navController.navigate(Screen.Filter.route) }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.filter),
                                            contentDescription = "Filter"
                                        )
                                    }
                                },
                                actions = {
                                    IconButton(onClick = { navController.navigate(Screen.Notification.route) }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.notifications),
                                            contentDescription = "Notifications"
                                        )
                                    }
                                })
                        } else {
                            CenterAlignedTopAppBar(
                                title = {
                                    Text(
                                        text = navController.currentBackStackEntryAsState().value?.destination?.route.toString()
                                            .capitalize(
                                                Locale.current
                                            )
                                    )
                                },
                                navigationIcon = {
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                                            contentDescription = "Back"
                                        )
                                    }
                                },
                            )
                        }
                    }, bottomBar = {
                        if (isTopBarVisible) BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
                            val mNavBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = mNavBackStackEntry?.destination

                            bottomNavScreens.forEach { screen ->
                                BottomNavigationItem(alwaysShowLabel = false,
                                    selectedContentColor = MaterialTheme.colorScheme.primary,
                                    unselectedContentColor = ColorDisabled,
                                    icon = {
                                        val iconDrawable = when (screen.route) {
                                            Screen.Profile.route -> R.drawable.profile
                                            Screen.Chat.route -> R.drawable.chat_history
                                            Screen.Home.route -> R.drawable.home

                                            else -> {
                                                R.drawable.home
                                            }
                                        }

//                                        Box(
//                                            modifier = if (selectedScreen == screen.route) Modifier
//                                                .background(
//                                                    color = ColorPrimary,
//                                                    shape = RoundedCornerShape(12.dp)
//                                                )
//                                                .padding(8.dp)
//                                            else Modifier
//                                        ) {
                                        Icon(
                                            imageVector = ImageVector.vectorResource(id = iconDrawable),
                                            contentDescription = screen.route,
                                            tint = if (selectedScreen == screen.route) ColorPrimary else Color.LightGray
                                        )
//                                        }

                                    },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        selectedScreen = screen.route
                                        navController.navigate(screen.route) {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    })
                            }
                        }

                    }) {
                        BlibberlyNavHost(
                            navController = navController, modifier = Modifier.padding(it)
                        )
                    }
                }
            }
        }

    }
}