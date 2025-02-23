package com.superbeta.blibberly_chat.presentation.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.stringPreferencesKey
import com.blibberly.profile_ops.data.model.ProfileOp
import com.blibberly.profile_ops.presentation.viewmodel.ProfileOpsViewModel
import com.superbeta.blibberly_auth.ui.theme.ColorSecondary
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.Calendar

@Composable
fun ProfileOpsMessageComponent(
    receiverUserEmail: String,
    profileOpsViewModel: ProfileOpsViewModel,
//    userName: String?
) {

    var currUser by remember {
        mutableStateOf<String?>(null)
    }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val userPreferencesDataStore = context.userPreferencesDataStore

    val profileOps by profileOpsViewModel.profileOpsState.collectAsState()
    val likedEmails by remember {
        mutableStateOf(profileOps?.likedProfiles ?: emptyList())
    }
    val disLikedEmails by remember {
        mutableStateOf(profileOps?.dislikedProfiles ?: emptyList())
    }
//    val likedUserProfiles by profileOpsViewModel.likedUserProfileState.collectAsState()

//    val dislikedProfiles by remember {
//        mutableStateOf(profileOps?.dislikedProfiles ?: emptyList())
//    }

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            userPreferencesDataStore.data.collect { preferences ->
                currUser = preferences[stringPreferencesKey("user_email")]
            }
        }
    }

    LaunchedEffect(key1 = currUser) {
        scope.launch(IO) {
            currUser?.let { profileOpsViewModel.getProfileOps(it) }
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(color = ColorSecondary, shape = RoundedCornerShape(8.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        BlibberlyIconButton(
            icon = Icons.Outlined.Close,
            contentDescription = "Dislike profile",
            onClick = {
                val currTimeStamp = Calendar.getInstance().time.toString()
                Log.i("Profile Ops", "$receiverUserEmail is Disliked")
                //add disliked profile to temp disliked profile list
                val tDislikedProfiles = disLikedEmails.toMutableList()
                tDislikedProfiles.add(
                    ProfileOp(
                        userEmail = receiverUserEmail,
                        timeStamp = currTimeStamp
                    )
                )

                //remove from liked list
                val tLikedProfiles = likedEmails.toMutableList()
                tLikedProfiles.removeIf { it.userEmail == receiverUserEmail }

                profileOps?.let {
                    profileOpsViewModel.setProfileOps(
                        profileOps = it.copy(
                            likedProfiles = tLikedProfiles,
                            dislikedProfiles = tDislikedProfiles
                        )
                    )
                }
            },
            bgColor = Color.White
        )

        BlibberlyIconButton(
            icon = Icons.Outlined.Favorite,
            contentDescription = "Like",
            onClick = {

                val currTimeStamp = Calendar.getInstance().time.toString()
                Log.i("Profile Ops", "$receiverUserEmail is Liked")

                //add liked profile to temp liked profile list
                val tLikedProfiles = likedEmails.toMutableList()
                tLikedProfiles.add(
                    ProfileOp(
                        userEmail = receiverUserEmail,
                        timeStamp = currTimeStamp
                    )
                )
                profileOps?.let {
                    profileOpsViewModel.setProfileOps(
                        it.copy(likedProfiles = tLikedProfiles)
                    )
                }
            },
            bgColor = Color.White
        )
    }
}

