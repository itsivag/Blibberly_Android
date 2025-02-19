package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_home.presentation.ui.components.profile.AboutCard
import com.superbeta.blibberly_home.presentation.ui.components.profile.BlibMojiCard
import com.superbeta.blibberly_home.presentation.ui.components.profile.InterestsCard
import com.superbeta.blibberly_home.presentation.ui.components.profile.LanguageCard
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun BlibberlyHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier,
    navigateToChat: (String, String) -> Unit,
    liveUsers: List<UserDataModel>,
    navigateToNoUsers: () -> Unit
) {
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
            LazyColumn(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.background)
                    .fillMaxWidth(),
            ) {
                item {
                    BlibMojiCard(userDataModel = currUser,
                        navigateToChat = { navigateToChat(currUser.email, currUser.name) })
                }
                item { AboutCard(user = currUser) }
//                item { LookingForCard() }
                item { ProfessionalCard() }
                item { LanguageCard(user = currUser) }
                item { InterestsCard(interests = currUser.interests) }
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




