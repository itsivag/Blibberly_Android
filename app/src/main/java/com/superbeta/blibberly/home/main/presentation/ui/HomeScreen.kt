package com.superbeta.blibberly.home.main.presentation.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.superbeta.blibberly.home.main.presentation.viewModel.HomeViewModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.utils.FontProvider
import com.superbeta.blibberly.utils.Screen
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier, navController: NavHostController,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = HomeViewModel.Factory)

) {

    val pagerState = rememberPagerState(pageCount = {
        10
    })

    HorizontalPager(
        state = pagerState, modifier = modifier.background(color = Color.White)
    ) { page ->

        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RectangleShape, modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    val pageOffset =
                        ((pagerState.currentPage - page) + pagerState.currentPageOffsetFraction).absoluteValue
                    alpha = lerp(
                        start = 0.5f, stop = 1f, fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }) {
            LazyColumn(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth(),
            ) {

                item { PhotoCard { navController.navigate(Screen.ChatList.route) } }
                item { AboutCard() }
                item { LanguageCard() }
//                item { ScoreCard() }


            }
//            }
        }
    }
}


@Composable
fun PhotoCard(navigateToChat: () -> Unit) {

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
            modifier = Modifier.fillMaxSize()
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
                    fontSize = 28.sp,
                    color = overLayTextColor
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
                    onClick = navigateToChat,
                    colors = IconButtonDefaults.iconButtonColors(containerColor = Color.White)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.chat),
                        contentDescription = "Chat",
                    )
                }
            }
        }
    }
}

@Composable
fun AboutCard() {
    val internalPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
    Text(
        text = "About",
        modifier = Modifier.padding(internalPadding),
        style = MaterialTheme.typography.titleLarge,
    )
    Text(
        text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(internalPadding)
    )

}

@Composable
fun LanguageCard() {
    val langList = listOf("Tamil", "English")
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        items(count = langList.size) { index ->
            Text(
                text = langList[index],
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
            progress = 0.8f, modifier = Modifier.fillMaxSize()
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
