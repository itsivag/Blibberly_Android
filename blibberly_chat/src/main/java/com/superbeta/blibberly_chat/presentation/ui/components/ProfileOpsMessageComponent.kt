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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel
import com.blibberly.blibberly_likes.presentation.ProfileOpsViewModel
import com.superbeta.blibberly_auth.theme.ColorSecondary
import java.util.Calendar

@Composable
fun ProfileOpsMessageComponent(
    userId: String?,
    profileViewModel: ProfileOpsViewModel,
    userName: String?
) {
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
            contentDescription = "Close",
            onClick = {
                Log.i("Profile Ops", "$userId is Disliked")
                profileViewModel.setProfileOps(
                    ProfileOpsDataModel(
                        userId = userId.toString(),
                        userEmail = userName.toString(),
                        isLiked = false,
                        isDisliked = true,
                        isMatched = false,
                        isReported = false,
                        likedTimestamp = Calendar.getInstance().time.toString()
                    )
                )
            },
            bgColor = Color.White
        )

        BlibberlyIconButton(
            icon = Icons.Outlined.Favorite,
            contentDescription = "Like",
            onClick = {
                Log.i("Profile Ops", "$userId is Liked")
                profileViewModel.setProfileOps(
                    ProfileOpsDataModel(
                        userId = userId.toString(),
                        userEmail = userName.toString(),
                        isLiked = true,
                        isDisliked = false,
                        isMatched = false,
                        isReported = false,
                        likedTimestamp = Calendar.getInstance().time.toString()
                    )
                )
            },
            bgColor = Color.White
        )
    }
}

