package com.superbeta.blibberly.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier, navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = {
        10
    })

    VerticalPager(
        state = pagerState,
        modifier = modifier.background(color = Color.Magenta)
    ) { page ->
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
//                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceAround,
//                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    contentScale = ContentScale.Crop,
                    painter = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxWidth()
                )
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(text = "John Wick", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                        Text(text = " Male", color = Color.DarkGray)
                    }
                    Text(text = "99")

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Unspecified
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
//                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(
                                text = "Tamil",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                            )
                            Text(
                                text = "English",
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .background(
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(vertical = 4.dp, horizontal = 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        FloatingActionButton(onClick = { navController.navigate("chat") }) {
                            Icon(
                                imageVector = Icons.Default.Send,
                                contentDescription = "Send Message"
                            )
                        }
                    }
                }
            }
        }
    }
}
