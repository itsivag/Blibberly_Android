package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_chat.R
import com.superbeta.blibberly_chat.data.model.MessageDataModel

@Composable
fun SenderChatBubble(currMessageDataModel: MessageDataModel) {

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

        Row(
            modifier = Modifier
                .padding(start = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(
                        topStart = 12.dp, topEnd = 12.dp, bottomEnd = 0.dp, bottomStart = 12.dp
                    )
                )
                .padding(2.dp),
//            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = currMessageDataModel.content,
                color = Color.White,
                fontSize = 14.sp,
//                fontStyle = ,
                modifier = Modifier.padding(end = 4.dp, top = 2.dp, bottom = 2.dp, start = 8.dp)
            )
            Row(
                modifier = Modifier.padding(top = 6.dp, start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = currMessageDataModel.timeStamp,
                    fontSize = 10.sp,
                    color = Color.White,
                )
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 2.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.double_tick),
                    contentDescription = "Read/Delivered",
                    tint = if (isMessageRead && isMessageDelivered) Color.Blue else Color.LightGray
                )
            }
        }
    }
}
