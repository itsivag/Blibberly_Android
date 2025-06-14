package com.superbeta.blibberly_chat.presentation.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.R
import com.superbeta.blibberly_chat.presentation.ui.components.MessageTextField
import com.superbeta.blibberly_chat.presentation.ui.components.ReceiverChatBubble
import com.superbeta.blibberly_chat.presentation.ui.components.SenderChatBubble
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import com.blibberly.profile_ops.presentation.viewmodel.ProfileOpsViewModel
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.presentation.ui.components.BlibMojiCircleAvatar
import com.superbeta.blibberly_chat.presentation.ui.components.ProfileOpsMessageComponent
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(
    navigateBack: () -> Unit,
    receiverUserName: String = "",
    receiverUserEmail: String = "",
    navigateToProfile: (String, String) -> Unit,
    messageViewModel: MessageViewModel = koinViewModel(),
    profileOpsViewModel: ProfileOpsViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {


    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val messages by messageViewModel.messageState.collectAsState()
    val userPreferencesDataStore = context.userPreferencesDataStore
    val messageLazyListState = rememberLazyListState()


    var currUser by remember {
        mutableStateOf<String?>(null)
    }
    var currUserProfile by remember {
        mutableStateOf<UserDataModel?>(null)
    }

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            Log.i(
                "Message Screen",
                "Message To Be Sent:  receiverEmail -> $receiverUserEmail "
            )
            //TODO create a separate view model
//            currUserProfile =
//                messageViewModel.getSpecificUserProfileWithEmail(email = receiverUserEmail)
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            //TODO get curr user email
//            currUser = authViewModel.retrieveSession()?.email
//            userPreferencesDataStore.data.collect { preferences ->
//                currUser = preferences[stringPreferencesKey("user_email")]
//            }
        }
    }

    LaunchedEffect(messages, currUser) {
        scope.launch(IO) {
            messageViewModel.collectMessages(
                currUserEmail = currUser,
                receiverEmail = receiverUserEmail
            )
        }
    }

    LaunchedEffect(messages) {
        messageLazyListState.scrollToItem(messageLazyListState.layoutInfo.totalItemsCount)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                        contentDescription = "Back",
                    )
                }
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            navigateToProfile(receiverUserEmail, receiverUserName)
                        }) {
                    currUserProfile?.let { BlibMojiCircleAvatar(photoMetaData = it.photoMetaData) }
                    Text(
                        text = receiverUserName,
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
                modifier = Modifier.weight(1f),
                state = messageLazyListState
            ) {
                item {
                    ProfileOpsMessageComponent(
                        receiverUserEmail = receiverUserEmail,
                        profileOpsViewModel = profileOpsViewModel,
//                        userName = receiverUserName,
                    )
                }
                items(count = messages.size) { i ->
                    val currMessage = messages[i]
                    if (currMessage.senderEmail == currUser) {
                        SenderChatBubble(currMessage)
                    } else {
                        ReceiverChatBubble(currMessage)
                    }
                }
            }
            currUser?.let { currUserEmail ->
                MessageTextField(
                    receiverEmail = receiverUserEmail,
                    currUserEmail = currUserEmail,
                )
            }
        }
    }
}

