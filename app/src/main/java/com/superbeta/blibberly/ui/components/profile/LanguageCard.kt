package com.superbeta.blibberly.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_auth.user.data.model.UserDataModel

@Composable
fun LanguageCard(user: UserDataModel) {
    val langList = listOf("Tamil", "English")

    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Text(
            text = "Can Speak In",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.DarkGray
//            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        ) {
            items(count = langList.size) { index ->
                Text(
                    text = langList[index],
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(16.dp)
                        )
//                        .background(
//                            color = MaterialTheme.colorScheme.secondary,
//                            shape = RoundedCornerShape(20.dp)
//                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}