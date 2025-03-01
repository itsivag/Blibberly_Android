package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_home.R

@Composable
fun ChatOrSkipCard(navigateToChat: () -> Unit, skipProfile: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        //skip button
        Image(
            modifier = Modifier.clickable {
                skipProfile()
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.thumbs_down),
            contentDescription = "Skip",
        )

        //chat button
        Image(
            modifier = Modifier.clickable {
                navigateToChat()
            },
            imageVector = ImageVector.vectorResource(id = R.drawable.thumbs_up),
            contentDescription = "Chat",
        )
//        }
    }
}
