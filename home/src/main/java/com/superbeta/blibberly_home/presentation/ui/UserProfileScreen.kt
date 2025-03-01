package com.superbeta.blibberly_home.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.R
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import com.superbeta.blibberly_home.presentation.ui.components.profile.AboutCard
import com.superbeta.blibberly_home.presentation.ui.components.profile.BlibMojiCard
import com.superbeta.blibberly_home.presentation.ui.components.profile.InterestsCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserProfileScreen(
    userEmail: String,
    navigateToMessageScreen: () -> Unit,
    navigateBack: () -> Unit,
    messageViewModel: MessageViewModel = koinViewModel(),
    userName: String,
) {

    var currUser by remember {
        mutableStateOf<UserDataModel?>(null)
    }
    LaunchedEffect(key1 = true) {
        //TODO create a separate profile view model and repo
//        currUser =
//            messageViewModel.getSpecificUserProfileWithEmail(email = userEmail)

    }

    Scaffold(topBar = {
        TopAppBar(navigationIcon = {
            IconButton(onClick = { navigateBack() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
                    contentDescription = "Back",
                )
            }
        }, title = {})
    }) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .background(color = MaterialTheme.colorScheme.background)
                .fillMaxWidth(),
        ) {
            currUser?.let {
                item {
                    BlibMojiCard(
                        userDataModel = it,
                        navigateToChat = navigateToMessageScreen
                    )
                }
                item { AboutCard(user = it) }
                item { InterestsCard(interests = it.interests) }
            }
        }
    }
}
