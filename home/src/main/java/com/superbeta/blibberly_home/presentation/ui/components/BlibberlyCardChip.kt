package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_auth.theme.ColorPrimary
import com.superbeta.blibberly_auth.utils.FontProvider


@Composable
fun BlibberlyCardChip(value: String) {
    Row(
        modifier = Modifier
            .padding(4.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = ColorPrimary),
                shape = RoundedCornerShape(12.dp)
            )
//            .background(color = ColorPrimary, shape = RoundedCornerShape(12.dp))
            .padding(8.dp)
    ) {
        Text(
            text = value,
            fontFamily = FontProvider.dmSansFontFamily,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 8.dp),
            fontWeight = FontWeight.SemiBold,
        )

    }
}