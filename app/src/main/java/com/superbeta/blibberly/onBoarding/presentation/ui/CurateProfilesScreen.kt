package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.superbeta.blibberly.R
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.utils.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CurateProfilesScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = UserViewModel.Factory)
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = Unit) {
        scope.launch {
            delay(3000)
            viewModel.uploadUserToDB()
        }.invokeOnCompletion {
            navController.navigate(Screen.Home.route)
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.padding(16.dp),
            composition = composition,
            progress = { progress },
        )
    }
}