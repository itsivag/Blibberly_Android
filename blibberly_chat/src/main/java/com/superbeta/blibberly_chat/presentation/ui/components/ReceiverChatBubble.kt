package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_chat.data.model.MessageDataModel

@Composable
fun ReceiverChatBubble(currMessageDataModel: MessageDataModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp), contentAlignment = Alignment.CenterStart
    ) {

        Row(
            modifier = Modifier
                .padding(end = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(
                        topStart = 12.dp, topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 0.dp
                    )
                )
                .padding(2.dp),
//            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = currMessageDataModel.content, color = Color.Black, fontSize = 14.sp,
                modifier = Modifier.padding(end = 4.dp, top = 2.dp, bottom = 2.dp, start = 8.dp)
            )
            Text(
                modifier = Modifier.padding(top = 6.dp, start = 4.dp, end = 4.dp),
                text = currMessageDataModel.timeStamp,
                fontSize = 10.sp,
                color = Color.DarkGray,
            )
        }
    }
}