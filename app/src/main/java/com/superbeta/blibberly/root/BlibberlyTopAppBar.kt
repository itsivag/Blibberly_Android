package com.superbeta.blibberly.root

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.utils.Screen

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BlibberlyTopAppBar(navController: NavHostController) {
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