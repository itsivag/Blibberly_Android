package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_home.R


@Composable
fun KarmaPointsCard(user: UserDataModel) {
    val emojiList = listOf(
        R.drawable.toxic_alert_emoji,
        R.drawable.risky_energy_emoji,
        R.drawable.good_vibes_emoji,
        R.drawable.golden_aura_emoji
    )
    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        ),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BlibberlyCardTitle(title = "Karma Points", emoji = "♾\uFE0F")

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(12.dp),
        ) {
            LinearProgressIndicator(
                progress = { 0.5f },
                strokeCap = StrokeCap.Square,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(emojiList.size) {
                    Image(
                        painter = painterResource(id = emojiList[it]),
                        contentDescription = "Golden Aura",
                        modifier = Modifier.size(80.dp),
                        colorFilter = ColorFilter.colorMatrix(colorMatrix = ColorMatrix().apply {
                            setToSaturation(
                                0f
                            )
                        }),
                        alpha = 0.2f
                    )
                }
            }

        }
    }
}
