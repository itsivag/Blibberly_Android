package com.superbeta.blibberly.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.imageLoader
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.extensions.dashedBorder
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfilePhotoScreen(
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = UserViewModel.Factory)
) {

    var photoUri: Uri? by remember { mutableStateOf(null) }

    val imagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
        }

    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    val imageLoader = LocalContext.current.imageLoader


    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.getUser()
            val userData: UserDataModel? = viewModel.userState.value
            if (userData != null) {
//                photoUri = Uri.parse(userData.photoMetaData)
            }
            Log.i("sivag", photoUri.toString())
        }
    }

    Text(
        text = "Photo",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(16.dp)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(screenHeight / 2)
            .padding(16.dp)
            .background(color = Color.Unspecified, shape = RoundedCornerShape(20.dp))
            .dashedBorder(width = 1.dp, radius = 20.dp, color = Color.DarkGray)
    ) {
        if (photoUri != null) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                model = photoUri,
                contentDescription = "Profile Picture",
                imageLoader = imageLoader,
                clipToBounds = true,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(20.dp))

            )
        } else {
            Text(
                text = "Click the pen button to add photo",
                modifier = Modifier.align(Alignment.Center),
                color = ColorDisabled
            )
        }
        FloatingActionButton(
            containerColor = Color.White,
            modifier = Modifier
                .align(alignment = Alignment.BottomEnd)
                .padding(16.dp),
            onClick = {
                scope.launch {
                    imagePickerLauncher.launch(
                        PickVisualMediaRequest(
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }.invokeOnCompletion {
//                    viewModel.updatePhotoMetaData(photoUri.toString())
                }
            }) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.edit),
                contentDescription = "Edit Photo"
            )
        }
    }
}