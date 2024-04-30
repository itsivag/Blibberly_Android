package com.superbeta.blibberly.chat

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import io.getstream.chat.android.compose.ui.messages.MessagesScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme
import io.getstream.chat.android.compose.viewmodel.messages.MessagesViewModelFactory

@Composable
fun ChatScreen(modifier: Modifier, navController: NavHostController) {
    val context = LocalContext.current
    ChatTheme {
        MessagesScreen(
            onBackPressed = { navController.popBackStack() },
            viewModelFactory = MessagesViewModelFactory(
                context = context,
                channelId = "messaging:123"
            ),

            )
    }
}

