package com.superbeta.blibberly_home.presentation.ui.components.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.google.gson.Gson


@Composable
fun InterestsCard(interests: String) {
    val interestsList = Gson().fromJson(interests, Array<String>::class.java)

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
            text = "Interests",
            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(8.dp)
            color = Color.DarkGray

        )

        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Adaptive(minSize = 50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 100.dp),
            userScrollEnabled = false
        ) {
            items(count = interestsList.size) { index ->
                Text(
                    text = interestsList[index],
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(vertical = 4.dp, horizontal = 8.dp)
                )
            }
        }
    }
}