package com.superbeta.blibberly_home.presentation.ui.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_auth.model.UserDataModel
import com.superbeta.blibberly_home.R

data class KarmaPointModel(val name: String, val emoji: Int)
sealed class KarmaEmojiTypes(val type: String) {
    data object ToxicAlert : KarmaEmojiTypes("Toxic Alert")
    data object RiskyEnergy : KarmaEmojiTypes("Risky Energy")
    data object GoodVibes : KarmaEmojiTypes("Good Vibes")
    data object GoldenAura : KarmaEmojiTypes("Golden Aura")
}

@Composable
fun KarmaPointsCard(user: UserDataModel) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val emojiList = listOf(
        KarmaPointModel(
            name = KarmaEmojiTypes.ToxicAlert.type, emoji =
            R.drawable.toxic_alert_emoji
        ),
        KarmaPointModel(
            name = KarmaEmojiTypes.RiskyEnergy.type,
            emoji =
            R.drawable.risky_energy_emoji,
        ),
        KarmaPointModel(
            name = KarmaEmojiTypes.GoodVibes.type,
            emoji =
            R.drawable.good_vibes_emoji,
        ),
        KarmaPointModel(
            name = KarmaEmojiTypes.GoldenAura.type, emoji =
            R.drawable.golden_aura_emoji
        ),
    )


    val karmaPoints = 85
    val selectedEmoji = when (karmaPoints) {
        in 0..25 -> {
            KarmaEmojiTypes.ToxicAlert.type
        }

        in 26..50 -> {
            KarmaEmojiTypes.RiskyEnergy.type
        }

        in 51..75 -> {
            KarmaEmojiTypes.GoodVibes.type
        }

        in 76..100 -> {
            KarmaEmojiTypes.GoldenAura.type
        }

        else -> {
            ""
        }
    }

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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
                    .padding(horizontal = 20.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFE9022C),
                                Color(0xFFFFEB3B),
                            )
                        )
                    )
            )

            val exposure = 150f
            val saturation = 0f // 0 = grayscale, 1 = original

            val grayscaleMatrix = floatArrayOf(
                0.3f * (1 - saturation) + saturation,
                0.59f * (1 - saturation),
                0.11f * (1 - saturation),
                0f,
                exposure,
                0.3f * (1 - saturation),
                0.59f * (1 - saturation) + saturation,
                0.11f * (1 - saturation),
                0f,
                exposure,
                0.3f * (1 - saturation),
                0.59f * (1 - saturation),
                0.11f * (1 - saturation) + saturation,
                0f,
                exposure,
                0f,
                0f,
                0f,
                1f,
                0f
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(emojiList.size) {
                    Box(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = painterResource(id = emojiList[it].emoji),
                            contentDescription = emojiList[it].name,
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    if (selectedEmoji == emojiList[it].name) {
                                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                        Toast
                                            .makeText(
                                                context,
                                                selectedEmoji,
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                                },
                            colorFilter = if (selectedEmoji != emojiList[it].name) ColorFilter.colorMatrix(
                                colorMatrix = ColorMatrix(
                                    grayscaleMatrix
                                )
                            ) else ColorFilter.colorMatrix(colorMatrix = ColorMatrix()),
                        )
                    }
                }
            }

        }
    }
}
