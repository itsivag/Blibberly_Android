package com.superbeta.blibberly.home.main.presentation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
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
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
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
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.theme.avatarBGColorsMap
import com.superbeta.blibberly.utils.FontProvider
import com.superbeta.blibberly_auth.user.data.model.PhotoMetaData
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier, navController: NavHostController,
    viewModel: MessageViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = MessageViewModel.Factory
    ),
//    homeViewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
//        factory = HomeViewModel.Factory
//    )
) {

    val liveUsers by viewModel.usersState.collectAsState()
    val scope = rememberCoroutineScope()
    val liveUserProfile by viewModel.userProfileState.collectAsState()

    LaunchedEffect(key1 = true) {
        scope.launch {
            viewModel.getUsers()
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            viewModel.getUserProfile()
        }
    }

    val pagerState = rememberPagerState(pageCount = {
        liveUserProfile.size
    })

    HorizontalPagerItem(pagerState, modifier, navController, liveUserProfile)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerItem(
    pagerState: PagerState,
    modifier: Modifier,
    navController: NavHostController,
    liveUsers: List<UserDataModel>
) {
    HorizontalPager(
        state = pagerState, modifier = modifier.background(color = Color.White)
    ) { page ->

        Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
            shape = RectangleShape,
            modifier = Modifier
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
                item { BlibMojiCard(photoMetaData = liveUsers[page].photoMetaData) }
//                item { PhotoCard(user = liveUsers[page]) { navController.navigate(Screen.ChatList.route) } }
                item { AboutCard(user = liveUsers[page]) }
                item { LanguageCard(user = liveUsers[page]) }
//                item { ScoreCard() }
            }
        }
    }
}


@Composable
fun PhotoCard(user: UserDataModel, navigateToChat: () -> Unit) {

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
                    text = user.name,
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
                Text(text = user.age.toString(), fontSize = 28.sp, color = overLayTextColor)
                Text(text = user.gender, color = overLayTextColor)

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
fun AboutCard(user: UserDataModel) {
    val internalPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
    Text(
        text = "About",
        modifier = Modifier.padding(internalPadding),
        style = MaterialTheme.typography.titleLarge,
    )
    Text(
        text = user.aboutMe,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(internalPadding)
    )

}

@Composable
fun LanguageCard(user: UserDataModel) {
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

@Composable
fun BlibMojiCard(photoMetaData: PhotoMetaData) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    avatarBGColorsMap[photoMetaData.bgColor]?.let {
        Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = SpringSpec(
                    stiffness = Spring.StiffnessLow,
                    dampingRatio = Spring.DampingRatioLowBouncy
                )
            )
            .height(screenHeight / 2)
            .background(color = it)
    }?.let {
        Box(
            modifier = it,
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .rotate(45f)
                    .fillMaxSize()
                    .scale(2f)
            ) {
                LazyHorizontalStaggeredGrid(
                    rows = StaggeredGridCells.Adaptive(minSize = 44.dp),
                    modifier = Modifier
                        .fillMaxSize(),
                    userScrollEnabled = false
                ) {
                    items(count = 300) {
                        Text(
                            text = photoMetaData.bgEmoji,
                            fontFamily = FontProvider.notoEmojiFontFamily,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                }
            }

            SubcomposeAsyncImage(
                model = photoMetaData.blibmojiUrl,
                contentDescription = "",
            ) {
                val state = painter.state
                if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                    CircularProgressIndicator()
                } else {
                    SubcomposeAsyncImageContent()
                }
            }

        }
    }
}
