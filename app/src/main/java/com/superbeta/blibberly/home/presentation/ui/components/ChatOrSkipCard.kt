package com.superbeta.blibberly.home.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly.R
import com.superbeta.blibberly_auth.theme.ColorDisabled
import com.superbeta.blibberly_auth.theme.ColorPrimary

@Composable
fun ChatOrSkipCard(navigateToChat: () -> Unit, skipProfile: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        //skip button
        IconButton(
            modifier = Modifier.size(72.dp),
            onClick = {
                skipProfile()
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = ColorDisabled)
        ) {
            Icon(
//                tint = Color.White,
                imageVector = ImageVector.vectorResource(id = R.drawable.close),
                contentDescription = "Chat",
            )
        }

        //chat button
        IconButton(
            modifier = Modifier.size(72.dp),
            onClick = {
                navigateToChat()
            },
            colors = IconButtonDefaults.iconButtonColors(containerColor = ColorPrimary)
        ) {
            Icon(
//                modifier = Modifier.size(48.dp),
                tint = Color.White,
                imageVector = ImageVector.vectorResource(id = R.drawable.chat),
                contentDescription = "Chat",
            )
        }
    }
}
