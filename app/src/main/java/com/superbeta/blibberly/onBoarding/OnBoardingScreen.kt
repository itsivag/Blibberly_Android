package com.superbeta.blibberly.onBoarding

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@Composable
fun OnBoardingScreen(modifier: Modifier, navController: NavHostController) {
    Column(modifier = modifier) {
        Text(text = "On Boarding Screen")
    }
}