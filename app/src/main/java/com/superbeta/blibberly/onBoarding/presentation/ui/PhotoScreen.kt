package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.profile.ProfilePhotoScreen
import com.superbeta.blibberly.ui.theme.components.PrimaryButton
import com.superbeta.blibberly.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(modifier: Modifier, navController: NavHostController) {

    val isButtonEnabled by remember {
        mutableStateOf(true)
    }

    Column {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                    contentDescription = "back"
                )
            }
        })
        Spacer(modifier = Modifier.weight(1f))
        ProfilePhotoScreen()
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            buttonText = "Skip",
            isButtonEnabled = isButtonEnabled
        ) {
            navController.navigate(Screen.Home.route)
        }
    }
}