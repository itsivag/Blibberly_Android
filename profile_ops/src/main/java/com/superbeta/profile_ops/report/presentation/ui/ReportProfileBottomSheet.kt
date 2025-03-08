package com.superbeta.profile_ops.report.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.superbeta.blibberly_components.colors.ColorDisabled
import com.superbeta.blibberly_components.colors.TextColorGrey
import com.superbeta.blibberly_utils.FontProvider
import com.superbeta.profile_ops.report.presentation.viewModel.ReportViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportProfileBottomSheet(
    sheetState: SheetState,
    changeBottomSheetVisibility: (Boolean) -> Unit,
    reportedUser: String,
    viewModel: ReportViewModel = koinViewModel(),
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
//    var otherReport by remember {
//        mutableStateOf(TextFieldValue())
//    }

    var selectedReport by remember {
        mutableStateOf("")
    }

    val haptics = LocalHapticFeedback.current
    val scrollState = rememberScrollState()


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
            repeat(reportCategories.size) {
                val currReportCategory = reportCategories[it]
                val currReportConcatText =
                    currReportCategory.first + " : " + currReportCategory.second
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
                            append(currReportCategory.first + " - ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = FontProvider.dmSansFontFamily,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        ) {
                            append(currReportCategory.second)
                        }
                    },
                    modifier = Modifier
                        .clickable {
                            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (selectedReport == currReportConcatText) {
                                selectedReport = ""
                            } else {
                                selectedReport = currReportConcatText
                            }

                        }
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .border(
                            width = 2.dp,
                            color = if (selectedReport == currReportConcatText) Color.Red else Color.Transparent,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(8.dp)
                )
            }
            //TODO add other report
//            Column(
//                Modifier
//                    .padding(8.dp)
//                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
//            ) {
//                TextFieldWithLabel(
//                    textFieldValue = otherReport,
//                    onTextFieldValueChange = { otherReport = it },
//                    labelText = "Other",
//                    placeHolderText = "Type any issue that doesn’t fit the above...",
//                    keyboardOptions = KeyboardOptions.Default,
//                )
//            }
        }

        PrimaryButton(
            isButtonEnabled = selectedReport.isNotEmpty(),
            buttonText = "Report And Block",
            buttonContainerColor = Color.White,
            textColor = if (selectedReport.isNotEmpty()) Color.Red else ColorDisabled,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            scope.launch {
                viewModel.registerProfileReport(
                    report = selectedReport,
                    reportedUser = reportedUser
                )
                //TODO remove this and add animation
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
