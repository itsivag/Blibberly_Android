package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BlibberlyIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    bgColor: Color
) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .padding(8.dp)
            .background(color = bgColor, shape = CircleShape)
            .padding(8.dp)
    ) {
        Icon(imageVector = icon, contentDescription = contentDescription)
    }
}
