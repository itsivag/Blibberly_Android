package com.superbeta.blibberly_chat.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_chat.R
import com.superbeta.blibberly_chat.data.Message
import com.superbeta.blibberly_chat.presentation.ui.components.MessageTextField
import com.superbeta.blibberly_chat.presentation.ui.components.ReceiverChatBubble
import com.superbeta.blibberly_chat.presentation.ui.components.SenderChatBubble

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ChatScreen() {

    val messages = listOf<Message>(
        Message(
            messageId = "1",
            content = "hi how are you",
            senderID = "1",
            receiverID = "2",
            timeStamp = "10:23 pm",
            isDelivered = false,
            isRead = false
        ),
        Message(
            messageId = "2",
            content = "hi,I'm fine!",
            senderID = "2",
            receiverID = "1",
            timeStamp = "10:24 pm",
            isDelivered = false,
            isRead = false
        ),
        Message(
            messageId = "3",
            content = "i have a doubt",
            senderID = "1",
            receiverID = "2",
            timeStamp = "10:25 pm",
            isDelivered = false,
            isRead = false
        ),
        Message(
            messageId = "4",
            content = "yes please ask",
            senderID = "2",
            receiverID = "1",
            timeStamp = "10:28 pm",
            isDelivered = false,
            isRead = false
        ),
    )
    
    val currUser = "1"
    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                    )
                }
            },
            title = {
                Row {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile picture",
                    )
                    Text(text = "John lawda", modifier = Modifier.padding(horizontal = 8.dp))
                }
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Menu",
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
        )
    }) { it ->
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(count = messages.size) { i ->
                    if (messages[i].senderID == currUser) {
                        SenderChatBubble()
                    } else {
                        ReceiverChatBubble()
                    }
                }

            }
            Row(
                modifier = Modifier
                    .align(alignment = Alignment.BottomCenter)
            ) {
                MessageTextField()
            }
        }
    }
}