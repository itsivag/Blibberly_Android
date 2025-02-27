package com.superbeta.blibberly.root

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.ColorPrimary
import com.superbeta.blibberly.utils.Screen
//import com.superbeta.blibberly_auth.theme.ColorDisabled
//import com.superbeta.blibberly_auth.theme.ColorPrimary

@Composable
fun BlibberlyBottomBar(
    navController: NavHostController,
    bottomNavScreens: List<Screen>,
    selectedScreen: String
) {
    var selectedScreen1 by rememberSaveable {
        mutableStateOf(selectedScreen)
    }

    BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background) {
        val mNavBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = mNavBackStackEntry?.destination

        bottomNavScreens.forEach { screen ->
            BottomNavigationItem(alwaysShowLabel = false,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = ColorDisabled,
                icon = {
                    val iconDrawable = when (screen.route) {
                        Screen.CurrUserProfile.route -> R.drawable.profile
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
                        tint = if (selectedScreen1 == screen.route) ColorPrimary else Color.LightGray
                    )
                    //                                        }

                },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    selectedScreen1 = screen.route
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
}