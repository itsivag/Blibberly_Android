package com.superbeta.blibberly.root

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.superbeta.blibberly.R
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly_home.presentation.ui.BLIBMOJI_BG_COLORS
import org.koin.androidx.compose.koinViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun BlibberlyTopAppBar(
    navigateToCurrUserProfile: () -> Unit,
    navigateToChatListScreen: () -> Unit,
    viewModel: UserViewModel = koinViewModel()
) {
    val avatarBGColorsMap = mapOf(
        BLIBMOJI_BG_COLORS.BLUE.toString() to Color.Blue,
        BLIBMOJI_BG_COLORS.WHITE.toString() to Color.White,
        BLIBMOJI_BG_COLORS.RED.toString() to Color.Red,
        BLIBMOJI_BG_COLORS.GRAY.toString() to Color.Gray,
        BLIBMOJI_BG_COLORS.CYAN.toString() to Color.Cyan,
        BLIBMOJI_BG_COLORS.BLACK.toString() to Color.Black,
        BLIBMOJI_BG_COLORS.DARKGRAY.toString() to Color.DarkGray,
        BLIBMOJI_BG_COLORS.GREEN.toString() to Color.Green,
        BLIBMOJI_BG_COLORS.MAGENTA.toString() to Color.Magenta,
        BLIBMOJI_BG_COLORS.YELLOW.toString() to Color.Yellow
    )

    val userState by viewModel.userState.collectAsStateWithLifecycle()

    LaunchedEffect(userState) {
        Log.i("BlibberlyTopAppBar", "UserState : " + userState.toString())
    }

    CenterAlignedTopAppBar(
        title = { Text(text = "Blibberly") },
        navigationIcon = {
            IconButton(onClick = {
                navigateToCurrUserProfile()
            }) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(
                            color = avatarBGColorsMap[userState?.photoMetaData?.bgColor ?: "WHITE"]
                                ?: Color.White, shape = CircleShape
                        ),
                ) {
                    SubcomposeAsyncImage(
                        modifier = Modifier.clip(CircleShape),
                        model = userState?.photoMetaData?.blibmojiUrl,
                        contentDescription = (userState?.name ?: "User") + "'s Blibmoji",
                        contentScale = ContentScale.Crop
                    ) {
                        val state = painter.state
                        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                            CircularProgressIndicator()
                        } else {
                            SubcomposeAsyncImageContent()
                        }
                    }
                }
            }
        },
        actions = {
            IconButton(onClick = { navigateToChatListScreen() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.chat),
                    contentDescription = "Chat",
                    modifier = Modifier.size(30.dp)
                )
            }
        }
    )
}