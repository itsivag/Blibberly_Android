package com.superbeta.blibberly_home.presentation.ui.components.profile

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import com.superbeta.blibberly_home.presentation.ui.components.BlibberlyCardChip
import com.superbeta.blibberly_home.presentation.ui.components.BlibberlyCardTitle


@Composable
fun InterestsCard(interests: String) {
    val interestsList = Gson().fromJson(interests, Array<String>::class.java)

//    Card(
//        colors = CardColors(
//            containerColor = Color.White,
//            contentColor = Color.Black,
//            disabledContainerColor = Color.LightGray,
//            disabledContentColor = Color.DarkGray
//        ),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(12.dp)
//    ) {
//        Text(
//            text = "Interests",
//            style = MaterialTheme.typography.titleMedium,
////            modifier = Modifier.padding(8.dp)
//            color = Color.DarkGray
//
//        )
//
//        LazyHorizontalStaggeredGrid(
//            rows = StaggeredGridCells.Adaptive(minSize = 50.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .heightIn(max = 100.dp),
//            userScrollEnabled = false
//        ) {
//            items(count = interestsList.size) { index ->
//                Text(
//                    text = interestsList[index],
//                    style = MaterialTheme.typography.bodyLarge,
//                    modifier = Modifier
//                        .padding(8.dp)
//                        .border(
//                            width = 1.dp,
//                            color = MaterialTheme.colorScheme.secondary,
//                            shape = RoundedCornerShape(16.dp)
//                        )
//                        .padding(vertical = 4.dp, horizontal = 8.dp)
//                )
//            }
//        }
//    }

//    val scrollState = rememberScrollState()

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
        BlibberlyCardTitle(title = "Interests", emoji = "\uD83E\uDDE9")

        LazyRow(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 12.dp, end = 12.dp, top = 8.dp)
//                .scrollable(scrollState, orientation = Orientation.Horizontal)
//                .fillMaxWidth()
        ) {
            items(interestsList.size) {
                BlibberlyCardChip(value = interestsList[it])
            }
        }
    }
}
