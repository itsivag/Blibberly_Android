package com.superbeta.blibberly.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.utils.FontProvider
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(modifier: Modifier, navController: NavHostController) {
    val pagerState = rememberPagerState(pageCount = {
        10
    })

    VerticalPager(
        state = pagerState, modifier = modifier.background(color = Color.White)
    ) { page ->
        Card(
//            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = RectangleShape, modifier = Modifier
//                .padding(8.dp)
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue

                    alpha = lerp(
                        start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                val config = LocalConfiguration.current
                val screenHeight = config.screenHeightDp.dp
                val overLayTextColor = Color.White

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight / 2f)
                ) {

                    Image(
                        contentScale = ContentScale.Crop,
                        painter = painterResource(id = R.drawable.placeholder),
                        contentDescription = "Profile Picture",
                        modifier = Modifier
                            .fillMaxSize()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        Row {
                            Text(
                                modifier = Modifier.padding(8.dp),
                                text = "Eve Maria",
                                fontFamily = FontProvider.bebasFontFamily,
                                fontSize = 28.sp, color = overLayTextColor
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = { /*TODO*/ }) {
                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = "Profile Menu",
                                    tint = overLayTextColor,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Row(modifier = Modifier.padding(8.dp)) {
                            Text(text = "22", fontSize = 28.sp, color = overLayTextColor)
                            Text(text = " F", color = overLayTextColor)

                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(
                                onClick = { navController.navigate("chat") },
                                colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Send,
                                    contentDescription = "Chat",
                                )
                            }
                        }
                    }
                }

                val horizontalPagerState = rememberPagerState(pageCount = {
                    2
                })



                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .background(color = Color.White)
                        .fillMaxWidth()
                ) {

                    TabButton("About", 0, horizontalPagerState)
                    TabButton("Score", 1, horizontalPagerState)
                }
                Divider()

                HorizontalPager(state = horizontalPagerState) { page ->
                    Column(
                        modifier = Modifier
                            .background(color = Color.White)
                            .padding(16.dp)
                            .height(screenHeight / 2f)
                            .fillMaxWidth(),
                    ) {
                        when (page) {
                            0 -> AboutCard()
                            1 -> ScoreCard()
                        }
                    }
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp),
////                            horizontalArrangement = Arrangement.SpaceEvenly
//                    ) {
//                        Text(
//                            text = "Tamil",
//                            modifier = Modifier
//                                .padding(horizontal = 4.dp)
//                                .background(
//                                    color = Color.LightGray, shape = RoundedCornerShape(8.dp)
//                                )
//                                .padding(vertical = 4.dp, horizontal = 8.dp)
//                        )
//                        Text(
//                            text = "English",
//                            modifier = Modifier
//                                .padding(horizontal = 4.dp)
//                                .background(
//                                    color = Color.LightGray, shape = RoundedCornerShape(8.dp)
//                                )
//                                .padding(vertical = 4.dp, horizontal = 8.dp)
//                        )
//                    }

                }
//            }
            }
        }
    }
}

@Composable
fun AboutCard() {
    Text(text = "About")
    Text(
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        style = MaterialTheme.typography.labelLarge,
        modifier = Modifier.padding(vertical = 8.dp)
    )

    Row(modifier = Modifier.height(IntrinsicSize.Min)) {
        Icon(
            imageVector = Icons.Outlined.LocationOn, contentDescription = "Location",
            modifier = Modifier.padding(horizontal = 2.dp)
        )
        Text(
            text = "Chennai, IND",
            modifier = Modifier.padding(horizontal = 2.dp)
        )
        Divider(
            modifier = Modifier
                .padding(horizontal = 4.dp)
                .width(1.dp)
                .fillMaxHeight(fraction = 0.75f)
        )
    }

    Row {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.user_identity),
            tint = Color.Black,
            contentDescription = "Identity"
        )
        Text(text = "Undergrad at AUC")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabButton(tabButtonText: String, scrollToPageIndex: Int, horizontalPagerState: PagerState) {

    val coroutineScope = rememberCoroutineScope()
    val selectedContainerColor = MaterialTheme.colorScheme.primary
    val selectedContentColor = Color.White

    TextButton(
        onClick = {
            coroutineScope.launch {
                horizontalPagerState.animateScrollToPage(scrollToPageIndex)
            }
        },
        shape = RectangleShape,
        colors = ButtonDefaults.textButtonColors(
            containerColor = selectedContainerColor,
            contentColor = selectedContentColor
        )
    ) {
        Text(text = tabButtonText)
    }
}

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
            progress = 0.8f,
            modifier = Modifier
                .fillMaxSize()
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
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
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
