package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_auth.theme.ColorPrimary
import com.superbeta.blibberly_auth.theme.TextColorGrey
import com.superbeta.blibberly_auth.model.UserDataModel
import com.superbeta.blibberly_auth.utils.FontProvider

@Composable
fun IceBreakerCard(user: UserDataModel) {
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
        BlibberlyCardTitle(title = "Icebreaker", emoji = "\uD83E\uDDCA")
        Row(
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, start = 12.dp, end = 4.dp)
                .fillMaxWidth()
        ) {
            repeat(2) {
                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = ColorPrimary,
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .weight(1f)
                        .padding(8.dp)

                ) {
                    Text(
                        text = "What's your toxic trait?",
                        fontFamily = FontProvider.dmSansFontFamily,
                        fontSize = 16.sp,
                        color = TextColorGrey,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
            }
        }

    }

}
