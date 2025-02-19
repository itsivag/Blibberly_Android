package com.superbeta.blibberly.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly.ui.ColorPrimary
import com.superbeta.blibberly.utils.FontProvider

@Composable
fun PrimaryButton(
    modifier: Modifier = Modifier,
    buttonContainerColor: Color = ColorPrimary,
    buttonText: String,
    isButtonEnabled: Boolean,
    textColor: Color = Color.White,
    hapticsEnabled: Boolean = false,
    onClickMethod: () -> (Unit),
) {

//    val internalModifier = Modifier
//        .fillMaxWidth()
//        .padding(vertical = 8.dp)

    val haptic = LocalHapticFeedback.current
    Button(
        modifier = modifier,
        onClick = {
            if (hapticsEnabled) {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
            }
            onClickMethod()
        },
        colors = ButtonDefaults.buttonColors(containerColor = buttonContainerColor),
        shape = RoundedCornerShape(24.dp),
        enabled = isButtonEnabled,
    ) {
        Text(
            text = buttonText,
            modifier = Modifier.padding(8.dp),
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontFamily = FontProvider.dmSansFontFamily, fontWeight = FontWeight.SemiBold,
                color = textColor
            )
        )
    }
}