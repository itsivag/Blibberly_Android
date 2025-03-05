package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.superbeta.blibberly_home.presentation.ui.BLIBMOJI_BG_COLORS
import com.superbeta.blibberly_home.presentation.ui.components.profile.AboutCard
import com.superbeta.blibberly_home.presentation.ui.components.profile.BlibMojiCard
import com.superbeta.blibberly_home.presentation.ui.components.profile.InterestsCard
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun BlibberlyHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier,
    navigateToChat: (String, String) -> Unit,
    liveUsers: List<com.superbeta.blibberly_models.UserDataModel>,
    navigateToNoUsers: () -> Unit
) {
    val avatarBGColorsMap = mapOf(
        BLIBMOJI_BG_COLORS.BLUE.toString() to Color.Blue,
        BLIBMOJI_BG_COLORS.WHITE.toString() to Color.White,
        BLIBMOJI_BG_COLORS.RED.toString() to Color.Red,
        BLIBMOJI_BG_COLORS.GRAY.toString() to Color.Gray,
        BLIBMOJI_BG_COLORS.CYAN.toString() to Color.Cyan,
        BLIBMOJI_BG_COLORS.BLACK.toString() to Color.Black,
        BLIBMOJI_BG_COLORS.DARKGRAY.toString() to Color.DarkGray,
        BLIBMOJI_BG_COLORS.GREEN.toString() to Color.Green,
        BLIBMOJI_BG_COLORS.MAGENTA.toString() to Color.Magenta,
        BLIBMOJI_BG_COLORS.YELLOW.toString() to Color.Yellow
    )

    val scope = rememberCoroutineScope()
    HorizontalPager(
        userScrollEnabled = false,
        state = pagerState,
        modifier = modifier.background(color = MaterialTheme.colorScheme.background)
    ) { page ->

        val currUser = liveUsers[page]
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
            avatarBGColorsMap[currUser.photoMetaData.bgColor]?.let {
                LazyColumn(
                    modifier = Modifier
                        .background(
                            it.copy(alpha = 0.1f),
                        )
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
                    contentPadding = PaddingValues(12.dp)
                ) {
                    item {
                        BlibMojiCard(userDataModel = currUser,
                            navigateToChat = { navigateToChat(currUser.email, currUser.name) })
                    }
                    item { BioCard(user = currUser) }
                    item { AboutCard(user = currUser) }
                    item { InterestsCard(interests = currUser.interests) }
                    item { ProfessionalCard(user = currUser) }
                    item { IceBreakerCard(user = currUser) }
                    item { KarmaPointsCard(user = currUser) }
                    item {
                        ChatOrSkipCard(navigateToChat = {
                            navigateToChat(
                                currUser.email,
                                currUser.name
                            )
                        }, skipProfile = {
                            scope.launch {
                                if (pagerState.currentPage + 1 >= pagerState.pageCount) {
                                    navigateToNoUsers()
                                } else {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}







