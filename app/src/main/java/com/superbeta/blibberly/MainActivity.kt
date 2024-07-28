package com.superbeta.blibberly

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.superbeta.blibberly.navigation.BlibberlyNavHost
import com.superbeta.blibberly.ui.theme.BlibberlyTheme
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_chat.data.remote.SocketHandlerImpl


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //socket
        SocketHandlerImpl.getSocket()

        val bottomNavScreens = listOf(
            Screen.Profile,
            Screen.Home,
            Screen.ChatList,
        )

        var isTopBarVisible by mutableStateOf(false)
        var isBottomNavBarVisible by mutableStateOf(false)

        setContent {
            BlibberlyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    when (navBackStackEntry?.destination?.route) {
                        Screen.Home.route -> {
                            isTopBarVisible = true
                            isBottomNavBarVisible = true
                        }

                        Screen.Profile.route -> {
                            isTopBarVisible = false
                            isBottomNavBarVisible = true
                        }

                        Screen.ChatList.route -> {
                            isTopBarVisible = false
                            isBottomNavBarVisible = true
                        }

                        else -> {
                            isTopBarVisible = false
                            isBottomNavBarVisible = false
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
                        }
//                        else {
//                            CenterAlignedTopAppBar(
//                                title = {
//                                    Text(
//                                        text = navController.currentBackStackEntryAsState().value?.destination?.route.toString()
//                                            .capitalize(
//                                                Locale.current
//                                            )
//                                    )
//                                },
//                                navigationIcon = {
//                                    IconButton(onClick = { navController.popBackStack() }) {
//                                        Icon(
//                                            imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
//                                            contentDescription = "Back"
//                                        )
//                                    }
//                                },
//                            )
//                        }
                    }, bottomBar = {
                        if (isBottomNavBarVisible) BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
                            val mNavBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = mNavBackStackEntry?.destination

                            bottomNavScreens.forEach { screen ->
                                BottomNavigationItem(alwaysShowLabel = false,
                                    selectedContentColor = MaterialTheme.colorScheme.primary,
                                    unselectedContentColor = ColorDisabled,
                                    icon = {
                                        val iconDrawable = when (screen.route) {
                                            Screen.Profile.route -> R.drawable.profile
                                            Screen.ChatList.route -> R.drawable.chat_history
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

    override fun onDestroy() {
        super.onDestroy()
        SocketHandlerImpl.disconnectSocket()
    }
}

