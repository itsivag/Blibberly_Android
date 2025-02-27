package com.superbeta.blibberly_auth.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.superbeta.blibberly_auth.components.PrimaryButton
import com.superbeta.blibberly_auth.theme.TextColorGrey
import com.superbeta.blibberly_auth.utils.FontProvider
import kotlinx.coroutines.launch

@Composable
fun UserFlowScreen(modifier: Modifier) {
    val userFlowImagesList = listOf(
        "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding.png",
        "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding%20-%202.png",
        "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding%20-%203.png",
        "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding%20-%204.png"
    )

    val pagerState = rememberPagerState(pageCount = { userFlowImagesList.size })
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

//    val userInfoState by viewModel.userInfoState.collectAsStateWithLifecycle()
//
//    LaunchedEffect(true) {
//        viewModel.getUserInfo()
//    }
//    LaunchedEffect(userInfoState) {
//        Log.i("UserFlowScreen", userInfoState.toString())
//    }

    Box(contentAlignment = Alignment.BottomCenter) {
        HorizontalPager(
            state = pagerState,
//            modifier = modifier.fillMaxSize()
        ) { page ->
            SubcomposeAsyncImage(
                contentScale = ContentScale.Crop,
                model = userFlowImagesList[page],
                contentDescription = "User Flow",
//                modifier = Modifier.fillMaxSize()
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }

//        if (pagerState.currentPage != userFlowImagesList.size - 1) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                TextButton(onClick = {
////                TODO    viewModel.loginWithBrowser(context)
//                }) {
//                    Text(
//                        text = "Sign In",
//                        fontFamily = FontProvider.dmSansFontFamily,
//                        fontWeight = FontWeight.SemiBold,
//                        fontSize = 22.sp,
//                        color = Color.White
//                    )
//                }
//                PrimaryButton(
//                    hapticsEnabled = true,
//                    modifier = Modifier,
//                    buttonText = "Continue",
//                    isButtonEnabled = true,
//                    buttonContainerColor = Color.White,
//                    textColor = TextColorGrey
//                ) {
//                    scope.launch {
//                        pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
//                    }
//                }
//            }
//        } else if (pagerState.currentPage == userFlowImagesList.size - 1) {
//            PrimaryButton(
//                hapticsEnabled = true,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                buttonText = "Sign In",
//                isButtonEnabled = true,
//                buttonContainerColor = Color.White,
//                textColor = TextColorGrey
//            ) {
////TODO                viewModel.loginWithBrowser(context)
////                scope.launch {
////                    pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
////                }
//            }
//        }
    }
}
