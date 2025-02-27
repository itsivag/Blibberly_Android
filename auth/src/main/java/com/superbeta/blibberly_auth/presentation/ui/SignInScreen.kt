package com.superbeta.blibberly_auth.presentation.ui

import android.app.Activity
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.superbeta.blibberly_auth.R
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.theme.ColorDisabled
import com.superbeta.blibberly_auth.theme.ColorPrimary
import com.superbeta.blibberly_auth.theme.TextColorGrey
import com.superbeta.blibberly_auth.utils.FontProvider
import org.koin.androidx.compose.koinViewModel

@Composable
fun SignInScreen(
    modifier: Modifier,
    viewModel: AuthViewModel = koinViewModel(),
) {
    val context = LocalContext.current

    val activity = remember(context) {
        context as? Activity ?: (context as? ContextWrapper)?.baseContext as? Activity
    }
    Box(
        modifier = modifier
            .fillMaxSize()
//            .padding(16.dp)
    ) {

        val userFlowImagesList = listOf(
            "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding.png",
            "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding%20-%202.png",
            "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding%20-%203.png",
            "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/User%20Flow//Onboarding%20-%204.png"
        )

        val pagerState = rememberPagerState(pageCount = { userFlowImagesList.size })
        val scope = rememberCoroutineScope()

        HorizontalPager(
            state = pagerState,
            modifier = modifier.fillMaxSize()
        ) { page ->
            SubcomposeAsyncImage(
                contentScale = ContentScale.Crop,
                model = userFlowImagesList[page],
                contentDescription = "User Flow",
                modifier = Modifier.fillMaxSize()
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImageContent()
                }
            }
        }

        Column(
            modifier = Modifier
                .background(color = Color.White.copy(alpha = 0.3f))
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        ) {
            Row(
                Modifier
                    .height(30.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(userFlowImagesList.size) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.White else Color.White.copy(
                            alpha = 0.5f
                        )
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)

                    )
                }
            }

            Button(
                onClick = {
                    if (activity != null) {
                        viewModel.signInWithGoogle(activity)
                    } else {
                        Log.e("SignInScreen", "Cannot get Activity reference")
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF2F2F2),
//                contentColor = TextColorGrey
                ),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
//                .height(40.dp),
            ) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)) {
                    Image(
                        imageVector = ImageVector.vectorResource(id = R.drawable.google),
                        contentDescription = "Google",
                        modifier = Modifier.padding(end = 10.dp)
                    )
                    Text(
                        text = "Sign In With Google",
                        color = Color(0xFF1F1F1F),
                        fontFamily = FontProvider.robotoFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }



            Text(
                text = buildAnnotatedString {
                    append("By signing in, you agree to our ")
                    withLink(
                        LinkAnnotation.Url("https://blibberly.com/termsAndConditions")
                    ) {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            ),
                        ) {
                            append("Terms & Conditions")
                        }
                    }
                    append(" and ")
                    withLink(
                        LinkAnnotation.Url("https://blibberly.com/privacyPolicy")
                    ) {
                        withStyle(
                            style = SpanStyle(
                                textDecoration = TextDecoration.Underline
                            )
                        ) {
                            append("Privacy Policy.")
                        }
                    }
                },
                textAlign = TextAlign.Center,
                fontFamily = FontProvider.dmSansFontFamily,
                modifier = Modifier.padding(16.dp)
            )

        }
    }

}
