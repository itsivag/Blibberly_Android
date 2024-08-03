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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.superbeta.blibberly_chat.data.model.SocketUserDataModelItem
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = MessageViewModel.Factory
    )
) {
    val chats by viewModel.usersState.collectAsState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch {
            viewModel.getUsers()
            viewModel.getNewUserConnected()
        }
    }

    LazyColumn(modifier = modifier) {
        item {
            TopAppBar(title = { Text(text = "Saved Chats") })
        }
        items(chats.size) { i ->
            ChatListItem(chats[i], navController)
        }
    }
}

@Composable
fun ChatListItem(
    socketUserDataModelItem: SocketUserDataModelItem,
    navController: NavHostController
) {
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
            text = socketUserDataModelItem.username.toString().capitalize(Locale.current),
            modifier = Modifier.padding(start = 8.dp)
        )

    }
    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
}

