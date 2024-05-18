package com.superbeta.blibberly.home.notifications

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier) {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                    contentDescription = "back"
                )
            }
        })
        Text(text = "notifications")
    }
}