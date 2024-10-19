package com.superbeta.blibberly_chat.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import com.blibberly.blibberly_likes.presentation.viewmodel.ProfileOpsViewModel
import com.superbeta.blibberly_chat.presentation.ui.components.ChatListItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    modifier: Modifier,
    navController: NavHostController,
    messageViewModel: MessageViewModel = koinViewModel(),
    profileViewModel: ProfileOpsViewModel = koinViewModel()
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
            Button(onClick = {
                messageViewModel.disconnectUserFromSocket()
            }, content = { Text(text = "Disconnect") })
        }
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
            Text(text = likedProfiles[i].userEmail, modifier = Modifier.padding(16.dp))
        }
    }
}



