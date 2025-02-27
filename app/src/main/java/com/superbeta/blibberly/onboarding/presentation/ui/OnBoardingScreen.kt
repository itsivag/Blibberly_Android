package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    navigateToAboutMe: () -> Unit,
//                     navController: NavHostController
) {
    BioScreen(
        modifier = modifier,
        navigateBack = navigateBack,
        navigateToAboutMe = navigateToAboutMe
    )
}





