package com.superbeta.blibberly_home.presentation.ui.components.profile

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.superbeta.blibberly_auth.model.UserDataModel
import com.superbeta.blibberly_home.R
import com.superbeta.blibberly_home.presentation.ui.BLIBMOJI_BG_COLORS
import com.superbeta.blibberly_home.utils.FontProvider

@Composable
fun BlibMojiCard(userDataModel: UserDataModel, navigateToChat: () -> Unit) {

    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val overLayTextColor = Color.White
    val outlineTextColor = Color.Black

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

    Box(
        modifier = Modifier
            .height(screenHeight / 2.5f)
            .background(color = Color.Transparent, shape = RoundedCornerShape(16.dp))
    ) {
        avatarBGColorsMap[userDataModel.photoMetaData.bgColor]?.let {
            Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = SpringSpec(
                        stiffness = Spring.StiffnessLow, dampingRatio = Spring.DampingRatioLowBouncy
                    )
                )
                .height(screenHeight / 2.5f)
                .background(color = it, shape = RoundedCornerShape(16.dp))
        }?.let {
            Box(
                modifier = it,
                contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    modifier = Modifier
                        .rotate(45f)
                        .fillMaxSize()
                        .scale(2f)
                ) {
                    LazyHorizontalStaggeredGrid(
                        rows = StaggeredGridCells.Adaptive(minSize = 44.dp),
                        modifier = Modifier
                            .fillMaxSize(),
                        userScrollEnabled = false
                    ) {
                        items(count = 300) {
                            Text(
                                text = userDataModel.photoMetaData.bgEmoji,
                                fontFamily = FontProvider.notoEmojiFontFamily,
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(4.dp),
                                color = Color.Gray
                            )
                        }
                    }
                }

                SubcomposeAsyncImage(
                    model = userDataModel.photoMetaData.blibmojiUrl,
                    contentDescription = userDataModel.name + "'s Blibmoji",
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        CircularProgressIndicator()
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }

                Box(
                    Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.linearGradient(
                                colorStops =
                                arrayOf(
                                    0.1f to Color.Black.copy(alpha = 0.5f),
                                    0.25f to Color.Transparent,
                                ),
                                start = Offset(0f, Float.POSITIVE_INFINITY),
                                end = Offset(0f, 0.25f)
                            ), shape = RoundedCornerShape(16.dp)
                        )
                )

            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = userDataModel.name,
                fontFamily = FontProvider.bebasFontFamily,
                fontSize = 28.sp,
                color = overLayTextColor,
            )

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.more_vert),
                contentDescription = "Profile Menu",
                tint = overLayTextColor,
            )
        }
    }
}