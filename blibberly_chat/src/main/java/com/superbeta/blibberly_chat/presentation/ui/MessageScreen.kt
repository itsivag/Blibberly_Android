package com.superbeta.blibberly_chat.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.superbeta.blibberly_chat.R
import com.superbeta.blibberly_chat.presentation.ui.components.MessageTextField
import com.superbeta.blibberly_chat.presentation.ui.components.ReceiverChatBubble
import com.superbeta.blibberly_chat.presentation.ui.components.SenderChatBubble
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    modifier: Modifier,
    navController: NavHostController,
    viewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = MessageViewModel.Factory
    )
) {

    val scope = rememberCoroutineScope()
    val messages by viewModel.messageState.collectAsState()
    val currUser = "1"

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                    )
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Profile picture",
                        modifier = Modifier.size(48.dp)
                    )
                    Text(
                        text = "John lawda",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
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
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.weight(1f)
            ) {
                items(count = messages.size) { i ->
                    val currMessage = messages[i]
                    if (currMessage.senderID == currUser) {
                        SenderChatBubble(currMessage)
                    } else {
                        ReceiverChatBubble(currMessage)
                    }
                }
            }
            MessageTextField()
        }
    }
}