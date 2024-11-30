package com.superbeta.blibberly.home.main.presentation.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superbeta.blibberly.home.main.presentation.ui.components.BlibberlyHorizontalPager
import com.superbeta.blibberly.ui.components.HomeScreenShimmerEffect
import com.superbeta.blibberly_chat.presentation.viewModels.HomeScreenState
import com.superbeta.blibberly_chat.presentation.viewModels.MessageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
//    navController: NavHostController,
    navigateToChat: (String, String) -> Unit,
    messageViewModel: MessageViewModel = koinViewModel(),
//    homeViewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
//        factory = HomeViewModel.Factory
//    ),
    navigateToNoUsers: () -> Unit

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
                    "HomeScreen", "Collecting live user from compose"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(key1 = liveUsers) {
        scope.launch {
            try {
                messageViewModel.getUserProfile()
                Log.i(
                    "HomeScreen", "Collecting live user profile from compose"
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    when (homeScreenState) {
        HomeScreenState.LIVE_USERS_PROFILE_RETRIEVAL_SUCCESS -> {
            BlibberlyHorizontalPager(
                pagerState,
                modifier,
                navigateToChat,
                liveUserProfile,
                navigateToNoUsers
            )
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


enum class BLIBMOJI_BG_COLORS {
    BLUE, WHITE, RED, GRAY, CYAN, BLACK, DARKGRAY, GREEN, MAGENTA, YELLOW

}






