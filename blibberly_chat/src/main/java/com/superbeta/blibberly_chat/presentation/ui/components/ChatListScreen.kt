package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.superbeta.blibberly_chat.data.ChatUser

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(modifier: Modifier, navController: NavHostController) {
    val chats = listOf<ChatUser>(
        ChatUser(userId = "1", name = "anna", photoUri = "", isOnline = false),
        ChatUser(userId = "2", name = "banana", photoUri = "", isOnline = false),
        ChatUser(userId = "3", name = "carrot", photoUri = "", isOnline = false),
        ChatUser(userId = "4", name = "donald", photoUri = "", isOnline = false),
    )

    LazyColumn(modifier = modifier) {
        item {
            TopAppBar(title = { Text(text = "Saved Chats") })
        }
        items(chats.size) {
            ChatListItem(chats[it], navController)
        }
    }
}

@Composable
fun ChatListItem(chatUser: ChatUser, navController: NavHostController) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth()
            .clickable {
                navController.navigate("messages")
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "profile picture",
            tint = Color.LightGray,
            modifier = Modifier.size(48.dp)
        )
        Text(
            fontSize = 20.sp,
            text = chatUser.name.capitalize(Locale.current),
            modifier = Modifier.padding(start = 8.dp)
        )

    }
    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
}

