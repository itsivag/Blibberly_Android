package com.superbeta.blibberly.home.main.presentation.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.superbeta.blibberly.ui.components.HomeScreenShimmerEffect
import com.superbeta.blibberly.ui.components.profile.AboutCard
import com.superbeta.blibberly.ui.components.profile.BlibMojiCard
import com.superbeta.blibberly.ui.components.profile.InterestsCard
import com.superbeta.blibberly.ui.components.profile.LanguageCard
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.user.data.model.UserDataModel
import com.superbeta.blibberly_chat.presentation.viewModels.HomeScreenState
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier, navController: NavHostController,
    messageViewModel: MessageViewModel = koinViewModel(),
//    homeViewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
//        factory = HomeViewModel.Factory
//    ),
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    val homeScreenState by messageViewModel.homeScreenState.collectAsStateWithLifecycle()
    val liveUsers by messageViewModel.usersState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope { Dispatchers.IO }
    val liveUserProfile by messageViewModel.userProfileState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = {
        liveUserProfile.size
    })

    Log.i("HomeScreen state", homeScreenState.toString())

    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                messageViewModel.getUsers()
                Log.i(
                    "MessageViewModel", "Collecting live user from compose"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                messageViewModel.getUserProfile()
                Log.i(
                    "MessageViewModel", "Collecting live user profile from compose"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    when (homeScreenState) {
        HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_SUCCESS -> {
            BlibberlyHorizontalPager(pagerState, modifier, navController, liveUserProfile)
        }

        HomeScreenState.LIVE_USERS_EMPTY -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No Live User Found")
            }
        }

        else -> {
            HomeScreenShimmerEffect(modifier, screenHeight)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BlibberlyHorizontalPager(
    pagerState: PagerState,
    modifier: Modifier,
    navController: NavHostController,
    liveUsers: List<UserDataModel>
) {
    HorizontalPager(
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
                        navigateToChat = { navController.navigate(Screen.Message.route + "/${currUser.email}/${currUser.name}") })
                }
                item { AboutCard(user = currUser) }
                item { LanguageCard(user = currUser) }
                item { InterestsCard(interests = currUser.interests) }
            }
        }
    }
}


enum class BLIBMOJI_BG_COLORS {
    BLUE, WHITE, RED, GRAY, CYAN, BLACK, DARKGRAY, GREEN, MAGENTA, YELLOW

}






