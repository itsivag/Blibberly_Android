package com.superbeta.blibberly.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun HomeScreenShimmerEffect(modifier: Modifier, screenHeight: Dp) {
    LazyColumn(modifier = modifier) {
        item {
            Card(
                modifier = Modifier
                    .height(screenHeight / 2)
                    .fillMaxWidth()
                    .shimmer()
                    .padding(16.dp),
            ) {}
        }
        items(count = 5) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shimmer()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Spacer(modifier = Modifier.padding(vertical = 40.dp))
            }
        }
    }

}