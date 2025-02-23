package com.superbeta.blibberly.onBoarding.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.wear.compose.material.Text
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.TextColorGrey
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly_home.utils.FontProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun CurateProfileScreen(
    modifier: Modifier,
//    navController: NavHostController,
    navigateToHome: () -> Unit,
    viewModel: UserViewModel = koinViewModel()
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_loading))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )
    val scope = rememberCoroutineScope()
    val userState = viewModel.userState.collectAsStateWithLifecycle()
    val loadingScreenHintList = listOf(
        "See something sus? Report it! \uD83D\uDEA8",
        "Stay safe, don’t share personal info too soon! 🔒",
        "Real talk > Small talk. Be yourself! 🎤",
        "Not feeling the convo? ‘Nah, Pass 👉’ works too!",
        "A compliment can go a long way. Try it! 😊",
        "Not vibing? No stress, just skip. 👉",
        "Respect gets respect. Keep it cool. 😎",
        "Good vibes only! Spread positivity. ✨",
        "Flirting? Keep it smooth, not creepy. 😏"
    )
    var hintText by remember {
        mutableStateOf("")
    }

    LaunchedEffect(true) {
        scope.launch {
            while (true) {
                hintText = loadingScreenHintList.random()
                delay(3000)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getUser()
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            viewModel.uploadUserToDB()
            //TODO remove this
            delay(5000)
        }.invokeOnCompletion {
            Log.i("User", "Email -> ${userState.value?.email}")
            navigateToHome()
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
//        Text(
//            text = "Loading fresh connections",
//            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(16.dp),
//            style = TextStyle(
//                textAlign = TextAlign.Center,
//                color = ColorPrimary,
//                fontFamily = FontProvider.poppinsFontFamily,
//                fontWeight = FontWeight.SemiBold,
//                fontSize = 24.sp
//            )
//        )

        LottieAnimation(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Center),
            composition = composition,
            progress = { progress },
        )
        Text(
            text = hintText,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            ColorDisabled.copy(alpha = 0.7f),
                            TextColorGrey.copy(alpha = 0.3f)
                        )
                    ),
//                    color = ColorPrimary,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(12.dp),
            style = TextStyle(
                textAlign = TextAlign.Center,
                color = TextColorGrey,
                fontFamily = FontProvider.dmSansFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
    }
}