package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_chat.data.ChatUser

@Preview(showBackground = true)
@Composable
fun ChatListScreen() {
    val chats = listOf<ChatUser>(
        ChatUser(userId = "1", name = "anna", photoUri = "", isOnline = false),
        ChatUser(userId = "2", name = "banana", photoUri = "", isOnline = false),
        ChatUser(userId = "3", name = "carrot", photoUri = "", isOnline = false),
        ChatUser(userId = "4", name = "donald", photoUri = "", isOnline = false),
    )

    LazyColumn {
        items(chats.size) {
            ChatListItem(chats[it])
        }
    }
}

@Composable
fun ChatListItem(chatUser: ChatUser) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "profile picture",
            tint = Color.LightGray
        )
        Text(
            text = chatUser.name.capitalize(Locale.current),
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

