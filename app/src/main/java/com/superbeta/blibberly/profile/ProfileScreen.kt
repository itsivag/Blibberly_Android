package com.superbeta.blibberly.profile

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.superbeta.blibberly.onBoarding.data.UserLocalDbService
import com.superbeta.blibberly.utils.RoomInstanceProvider

@Composable
fun ProfileScreen(modifier: Modifier, navController: NavHostController) {

    val context = LocalContext.current

    val userLocalDbService = UserLocalDbService(RoomInstanceProvider.getDb(context))

    LazyColumn(modifier = modifier) {
        item {
            ProfilePhotoScreen()
        }
        item {
            ProfileBioScreen(userLocalDbService)
        }
        item {
            ProfileAboutMeScreen(userLocalDbService)
        }
    }
}