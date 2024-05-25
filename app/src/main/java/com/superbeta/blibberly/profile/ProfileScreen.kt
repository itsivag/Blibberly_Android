package com.superbeta.blibberly.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.user.data.UserLocalDbService
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.utils.RoomInstanceProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier, navController: NavHostController,
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = UserViewModel.Factory)
) {
    val context = LocalContext.current

//    val userLocalDbService = UserLocalDbService(RoomInstanceProvider.getDb(context))
//    val userViewModel : UserViewModel by activityViewModels()

    val userState by viewModel.userState.collectAsStateWithLifecycle()

    LazyColumn(modifier = modifier) {
        item {
            CenterAlignedTopAppBar(title = { Text(text = "Profile") }, navigationIcon = {
//                IconButton(onClick = { navController.popBackStack() }) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
//                        contentDescription = "back"
//                    )
//                }
            })
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