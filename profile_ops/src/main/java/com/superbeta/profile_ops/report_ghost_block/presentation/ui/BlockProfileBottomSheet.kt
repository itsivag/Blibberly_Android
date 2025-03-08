package com.superbeta.profile_ops.report_ghost_block.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_components.buttons.PrimaryButton
import com.superbeta.blibberly_components.colors.TextColorGrey
import com.superbeta.blibberly_utils.FontProvider
import com.superbeta.profile_ops.report_ghost_block.presentation.viewModel.ReportGhostBlockViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlockProfileBottomSheet(
    sheetState: SheetState,
    changeBottomSheetVisibility: (Boolean) -> Unit,
    blockedUser: String,
    viewModel: ReportGhostBlockViewModel = koinViewModel(),
) {
    val scope = rememberCoroutineScope()
    val haptics = LocalHapticFeedback.current
    val scrollState = rememberScrollState()

    val blockMessages = listOf(
        "🔒 They won’t find your profile or contact you anymore.",
        "📵 You won’t see their profile in matches or chats.",
        "🚨 They won't notified that you blocked them.",
//        "⏳ This action is permanent. If you change your mind, you’ll need to unblock them manually.",
        "⚠️ Want to report instead? If this user was inappropriate, you can also report them."
    )

    ModalBottomSheet(
        shape = RoundedCornerShape(topEnd = 12.dp, topStart = 12.dp),
        onDismissRequest = {
            changeBottomSheetVisibility(false)
        },
        sheetState = sheetState,
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(12.dp)
        ) {

            repeat(blockMessages.size) {
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
                            append(blockMessages[it])
                        }
//                        withStyle(
//                            style = SpanStyle(
//                                fontFamily = FontProvider.dmSansFontFamily,
//                                fontSize = 14.sp,
//                                fontWeight = FontWeight.Medium,
//                                color = Color.Gray
//                            )
//                        ) {
//                            append(currReportCategory.second)
//                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .padding(8.dp)
                )
            }
        }

        PrimaryButton(
            buttonText = "Block",
//            buttonContainerColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            scope.launch {
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                viewModel.blockUser(blockedUser)
                //TODO remove this and add dialog
                delay(1000)
                sheetState.hide()
            }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    changeBottomSheetVisibility(false)
                }
            }
        }
    }
}
