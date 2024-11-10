package com.superbeta.blibberly.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.gson.Gson


@Composable
fun InterestsCard(interests: String) {
    val interestsList = Gson().fromJson(interests, Array<String>::class.java)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Interests",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(8.dp)
        )

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
        ) {
            items(count = interestsList.size) { index ->
                Text(
                    text = interestsList[index],
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}