package com.superbeta.blibberly_chat.presentation.ui

import android.util.Log
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.preferences.core.stringPreferencesKey
import com.blibberly.profile_ops.presentation.viewmodel.ProfileOpsViewModel
import com.superbeta.blibberly_auth.user.data.model.PhotoMetaData
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.presentation.ui.components.ChatListItem
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import com.superbeta.blibberly_auth.user.data.model.UserDataModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    modifier: Modifier,
    navigateToMessage: (String, String) -> Unit,
    messageViewModel: MessageViewModel = koinViewModel(),
    profileOpsViewModel: ProfileOpsViewModel = koinViewModel(),
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val userPreferencesDataStore = context.userPreferencesDataStore

    val profileOps by profileOpsViewModel.profileOpsState.collectAsState()
    val userProfiles by messageViewModel.userProfileState.collectAsState()
    val likedUserProfiles by profileOpsViewModel.likedUserProfileState.collectAsState()

    var currUser by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            userPreferencesDataStore.data.collect { preferences ->
                currUser = preferences[stringPreferencesKey("user_email")]
            }
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            messageViewModel.getUsers()
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            messageViewModel.getUserProfile()
        }
    }

    LaunchedEffect(key1 = currUser) {
        scope.launch(IO) {
            currUser?.let { profileOpsViewModel.getProfileOps(it) }
        }
    }

    LaunchedEffect(key1 = profileOps) {
        scope.launch(IO) {
            profileOpsViewModel.getLikedUserProfiles()
        }
    }


    LazyColumn(modifier = modifier) {

//        item {
//            Button(onClick = {
//                messageViewModel.disconnectUserFromSocket()
//            }, content = { Text(text = "Disconnect") })
//        }
        item {
            TopAppBar(title = { Text(text = "Live Chats") })
        }
        items(userProfiles.size) { i ->
            val email = userProfiles[i].email
            val userName = userProfiles[i].name

            ChatListItem(
                userProfile = userProfiles[i],
                navigateToMessage = {
                    navigateToMessage(email, userName)
                })
        }
//        item {
//            TopAppBar(title = { Text(text = "Matched Chats") })
//        }
//        items(matchedProfiles.size) { i ->
//            ChatListItem(
//                userProfile = userProfiles[i],
//                navigateToMessage = {
//                    navigateToMessage(userProfiles[i].email, userProfiles[i].name)
//                })
//        }
        item {
            TopAppBar(title = { Text(text = "Liked Chats") })
        }
//        items(profileOps?.likedProfiles?.size ?: 0) { i ->
//            val email = profileOps?.likedProfiles?.get(i)?.userEmail
//            email?.let { Text(text = it) }
//        }

        Log.i("XXXTNX", likedUserProfiles.toString())

        items(likedUserProfiles.size) { i ->
            val email = likedUserProfiles[i].email
            val userName = likedUserProfiles[i].name
            val t = likedUserProfiles[i]
            val tUserDataModel = UserDataModel(
                email = t.email,
                name = t.name,
                age = t.age,
                gender = t.gender,
                height = t.height,
                weight = t.weight,
                aboutMe = t.aboutMe,
                interests = t.interests,
                photoMetaData = PhotoMetaData(
                    blibmojiUrl = t.photoMetaData.blibmojiUrl,
                    bgEmoji = t.photoMetaData.bgEmoji,
                    bgColor = t.photoMetaData.bgColor
                ),
                fcmToken = t.fcmToken
            )

            ChatListItem(
                userProfile = tUserDataModel,
                navigateToMessage = {
                    navigateToMessage(email, userName)
                })
        }
    }
}



