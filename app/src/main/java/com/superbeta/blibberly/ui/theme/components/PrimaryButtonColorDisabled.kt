package com.superbeta.blibberly.ui.theme.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly.ui.theme.ColorDisabled

@Composable
fun PrimaryButtonColorDisabled(
    modifier: Modifier,
    buttonText: String,
    isButtonEnabled: Boolean,
    onClickMethod: () -> (Unit)
) {

//    val internalModifier = Modifier
//        .fillMaxWidth()
//        .padding(vertical = 8.dp)

    Button(
        modifier = modifier,
        onClick = { onClickMethod() },
        shape = RoundedCornerShape(14.dp),
        enabled = isButtonEnabled,
        colors = ButtonDefaults.buttonColors(containerColor = ColorDisabled)
    ) {
        Text(
            text = buttonText,
            modifier = Modifier.padding(8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
        )
    }
}