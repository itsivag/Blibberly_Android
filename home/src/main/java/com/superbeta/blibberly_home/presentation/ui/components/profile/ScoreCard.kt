package com.superbeta.blibberly_home.presentation.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ScoreCard() {
    Text(text = "Affinity Score")

    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        LinearProgressIndicator(
            progress = { 0.8f },
            modifier = Modifier.fillMaxSize(),
        )

        Text(
            text = "", modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterStart)
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .align(Alignment.CenterEnd)
                .fillMaxHeight(), verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "🧑🏼‍💻 Coding",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(
                        color = Color.White, shape = RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
            Text(
                text = "🎧 Music",
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .background(
                        color = Color.White, shape = RoundedCornerShape(20.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp)
            )
        }


    }
}
