package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BlibberlyCardTitle(title: String, emoji: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp)
                .weight(1f)
        )
        Text(
            text = emoji,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(end = 16.dp, top = 16.dp)
        )
    }
}