package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_models.UserDataModel

@Composable
fun ChatListItem(
//    socketUserDataModelItem: SocketUserDataModelItem,
    navigateToMessage: () -> Unit,
    userProfile: com.superbeta.blibberly_models.UserDataModel,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 12.dp)
            .fillMaxWidth()
            .clickable {
                navigateToMessage()
//                navController.navigate("messages/" + socketUserDataModelItem.userID + "/" + socketUserDataModelItem.username)
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        BlibMojiCircleAvatar(photoMetaData = userProfile.photoMetaData)
        Text(
            fontSize = 20.sp,
            text = userProfile.name.capitalize(Locale.current),
            modifier = Modifier.padding(start = 8.dp)
        )

    }
    HorizontalDivider(color = Color.LightGray.copy(alpha = 0.3f))
}