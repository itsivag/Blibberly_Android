package com.superbeta.blibberly.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.superbeta.blibberly.user.presentation.UserViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrUserProfileScreen(
    modifier: Modifier,
//    navController: NavHostController,
    viewModel: UserViewModel = koinViewModel()
) {
    val context = LocalContext.current

//    val userLocalDbService = UserLocalDbService(RoomInstanceProvider.getDb(context))
//    val userViewModel : UserViewModel by activityViewModels()

    val userState by viewModel.userState.collectAsStateWithLifecycle()

    LazyColumn(modifier = modifier) {
        item {
            TopAppBar(title = { Text(text = "Profile") })
        }
        item {
            ProfilePhotoScreen()
        }
        item {
            ProfileBioScreen()
        }
        item {
            ProfileAboutMeScreen()
        }
    }
}