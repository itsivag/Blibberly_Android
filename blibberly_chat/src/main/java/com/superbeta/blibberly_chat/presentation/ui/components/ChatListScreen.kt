package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.superbeta.blibberly_chat.presentation.viewModels.ProfileOpsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    modifier: Modifier,
    navController: NavHostController,
    messageViewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = MessageViewModel.Factory
    ),
    profileViewModel: ProfileOpsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = ProfileOpsViewModel.Factory)
) {
    val chats by messageViewModel.usersState.collectAsState()
    val matchedProfiles by profileViewModel.matchedProfilesState.collectAsState()
    val likedProfiles by profileViewModel.likedProfilesState.collectAsState()

    LaunchedEffect(key1 = true) {
        messageViewModel.getUsers()
    }

    LaunchedEffect(key1 = true) {
        profileViewModel.getMatchedProfiles()
    }

    LaunchedEffect(key1 = true) {
        profileViewModel.getLikedProfiles()
    }

    LazyColumn(modifier = modifier) {
        item {
            TopAppBar(title = { Text(text = "Matched Chats") })
        }
        items(chats.size) { i ->
            ChatListItem(chats[i], navController)
        }
        item {
            TopAppBar(title = { Text(text = "Liked Chats") })
        }
        items(likedProfiles.size) { i ->
            Text(text = likedProfiles[i].userId, modifier = Modifier.padding(16.dp))
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
                navController.navigate("messages/" + socketUserDataModelItem.userID + "/" + socketUserDataModelItem.username)
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

