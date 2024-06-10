package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_chat.data.Message

@Composable
fun ReceiverChatBubble(currMessage: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), contentAlignment = Alignment.CenterStart
    ) {

        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(
                        topStart = 16.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 0.dp
                    )
                )
                .padding(8.dp), horizontalAlignment = Alignment.End
        ) {
            Text(text = currMessage.content, color = Color.Black, fontSize = 14.sp)
            Text(
                text = currMessage.timeStamp,
                fontSize = 12.sp,
                color = Color.DarkGray,
            )
        }
    }
}