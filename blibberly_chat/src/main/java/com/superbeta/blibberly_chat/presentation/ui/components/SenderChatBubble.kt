package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_chat.R

@Preview(showBackground = true)
@Composable
fun SenderChatBubble() {

    var isMessageRead by remember {
        mutableStateOf(false)
    }

    var isMessageDelivered by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.CenterEnd
    ) {

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(
                        topStart = 12.dp, topEnd = 12.dp, bottomEnd = 0.dp, bottomStart = 12.dp
                    )
                )
                .padding(8.dp), horizontalAlignment = Alignment.End
        ) {
            Text(text = "Hi Bro, How are you?", color = Color.White, fontSize = 16.sp)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = "11:52 PM",
                    fontSize = 12.sp,
                    color = Color.White,
                )
                Icon(
                    modifier = Modifier.padding(start = 4.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.double_tick),
                    contentDescription = "Read/Delivered",
                    tint = if (isMessageRead && isMessageDelivered) Color.Blue else Color.LightGray
                )
            }
        }
    }
}
