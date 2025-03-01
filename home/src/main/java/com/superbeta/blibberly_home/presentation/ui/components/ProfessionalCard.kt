package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_auth.utils.FontProvider
import com.superbeta.blibberly_home.R

@Composable
fun ProfessionalCard(user: UserDataModel) {

    val languagesList = listOf("Tamil", "English", "Hindi")

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
        BlibberlyCardTitle(title = "My Grind", emoji = "⚒\uFE0F")
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.work),
                contentDescription = "Work or Study", modifier = Modifier.padding(end = 4.dp)
            )
            Text(
                text = "Software Developer",
                fontFamily = FontProvider.dmSansFontFamily,
                fontSize = 18.sp,
//            color = TextColorGrey,
                fontWeight = FontWeight.SemiBold,
            )
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth())

        BlibberlyCardTitle(title = "I can speak", emoji = "\uD83D\uDDE3\uFE0F")

        LazyRow(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 12.dp, end = 12.dp, top = 8.dp)
        ) {
            items(languagesList.size) {
                BlibberlyCardChip(value = languagesList[it])
            }
        }
    }

}