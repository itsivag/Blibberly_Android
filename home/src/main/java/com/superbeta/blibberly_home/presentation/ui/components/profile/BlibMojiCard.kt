package com.superbeta.blibberly_home.presentation.ui.components.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.superbeta.blibberly_auth.components.PrimaryButton
import com.superbeta.blibberly_auth.theme.ColorDisabled
import com.superbeta.blibberly_auth.theme.TextColorGrey
import com.superbeta.blibberly_home.R
import com.superbeta.blibberly_home.presentation.ui.BLIBMOJI_BG_COLORS
import com.superbeta.blibberly_home.presentation.ui.components.TextFieldWithLabel
import com.superbeta.blibberly_home.utils.FontProvider
import com.superbeta.blibberly_models.UserDataModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
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

    var showProfileOperations by rememberSaveable {
        mutableStateOf(false)
    }

    var showReportProfileBottomSheet by remember {
        mutableStateOf(false)
    }

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

            AnimatedVisibility(
                showProfileOperations,
                enter = EnterTransition.None,
                exit = ExitTransition.None
            ) {
                ProfileOperationsCard(
                    it,
                    showReportProfileBottomSheet = {
                        showReportProfileBottomSheet = true
                    })
            }

            AnimatedVisibility(
                !showProfileOperations,
                enter = slideInHorizontally(),
                exit = slideOutHorizontally()
            ) {
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
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (!showProfileOperations) {
                Text(
                    text = userDataModel.name,
                    fontFamily = FontProvider.bebasFontFamily,
                    fontSize = 28.sp,
                    color = overLayTextColor,
                )
            } else {
                Spacer(modifier = Modifier)
            }

            Icon(
                imageVector = ImageVector.vectorResource(id = if (showProfileOperations) R.drawable.close else R.drawable.more_vert),
                contentDescription = "Profile Menu",
                tint = overLayTextColor,
                modifier = Modifier.clickable {
                    showProfileOperations = !showProfileOperations
                }
            )
        }

        //report profile bottom sheet
        if (showReportProfileBottomSheet)
            ReportProfileBottomSheet(
                sheetState = rememberModalBottomSheetState(),
                changeBottomSheetVisibility = {
                    showReportProfileBottomSheet = it
                })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportProfileBottomSheet(
    sheetState: SheetState,
    changeBottomSheetVisibility: (Boolean) -> Unit
) {
    val reportCategories = listOf(
        "Harassment/Bullying" to "User is being rude, aggressive, or threatening.",
        "Hate Speech" to "Discriminatory or offensive language.",
        "Inappropriate Content" to "Sharing NSFW, violent, or disturbing content.",
        "Spam/Flooding" to "Repeated messages, ads, or self-promotion.",
        "Privacy Violation" to "Sharing personal details without consent.",
        "Fake Profile/Bot" to "Suspicious or automated behavior.",
    )
    val scope = rememberCoroutineScope()
    var otherReportCategory by remember {
        mutableStateOf(TextFieldValue())
    }

    ModalBottomSheet(
        shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
        onDismissRequest = {
            changeBottomSheetVisibility(false)
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            repeat(reportCategories.size) {
                val reportCategory = reportCategories[it]
                Text(
                    buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontProvider.dmSansFontFamily,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = TextColorGrey
                            )
                        ) {
                            append(reportCategory.first + " - ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontProvider.dmSansFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = C
                            )
                        ) {
                            append(reportCategory.second)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(8.dp)
                )
            }
            Column(
                Modifier
                    .padding(8.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
            ) {
                TextFieldWithLabel(
                    textFieldValue = otherReportCategory,
                    onTextFieldValueChange = { otherReportCategory = it },
                    labelText = "Other",
                    placeHolderText = "Type any issue that doesn’t fit the above...",
                    keyboardOptions = KeyboardOptions.Default,
                )
            }
        }

        PrimaryButton(
            buttonText = "Report And Block",
            buttonContainerColor = Color.White,
            textColor = Color.Red,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            scope.launch {
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    changeBottomSheetVisibility(false)
                }
            }
        }
    }
}

@Composable
fun ProfileOperationsCard(modifier: Modifier, showReportProfileBottomSheet: () -> Unit) {
    Column(modifier = modifier) {
        Row(Modifier.padding(8.dp)) {
            ProfileOperationItem(content = "About", icon = R.drawable.about, modifier =
            Modifier.Companion
                .weight(1f)
                .padding(8.dp), onCLick = {})
            ProfileOperationItem(
                content = "Ghost", icon = R.drawable.ghost, modifier =
                Modifier.Companion
                    .weight(1f)
                    .padding(8.dp), onCLick = {}
            )
        }

        Row(Modifier.padding(horizontal = 8.dp)) {
            ProfileOperationItem(
                content = "Block", icon = R.drawable.block, modifier =
                Modifier.Companion
                    .weight(1f)
                    .padding(8.dp), onCLick = {}
            )

            ProfileOperationItem(content = "Report",
                icon = R.drawable.report,
                modifier =
                Modifier.Companion
                    .weight(1f)
                    .padding(8.dp),
                onCLick = {
                    showReportProfileBottomSheet()
                })
        }

    }
}

@Composable
private fun ProfileOperationItem(
    content: String,
    icon: Int,
    modifier: Modifier,
    onCLick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onCLick() },
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.3f))
    ) {
        Text(
            text = content,
            fontFamily = FontProvider.dmSansFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            modifier = Modifier.padding(8.dp)
        )
        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = content
        )
    }
}