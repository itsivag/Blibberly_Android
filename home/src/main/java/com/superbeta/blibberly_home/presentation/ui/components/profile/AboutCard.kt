package com.superbeta.blibberly_home.presentation.ui.components.profile

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_auth.utils.FontProvider
import com.superbeta.blibberly_home.presentation.ui.components.BlibberlyCardTitle

@Composable
fun AboutCard(user: UserDataModel) {

    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        ),
        modifier = Modifier
            .fillMaxWidth()
//            .scrollable(scrollState, orientation = Orientation.Horizontal)
//            .padding(16.dp),
    ) {
        BlibberlyCardTitle(title = "About me", emoji = "\u200D\uD83D\uDC4B\uD83C\uDFFC\uFE0F")
        Text(
            text = user.aboutMe,
            fontFamily = FontProvider.dmSansFontFamily,
            fontSize = 18.sp,
//            color = TextColorGrey,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(16.dp)
        )
    }
}