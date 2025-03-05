package com.superbeta.blibberly_home.presentation.ui

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
import com.superbeta.blibberly_home.presentation.HomeScreenState
import com.superbeta.blibberly_home.presentation.ui.components.BlibberlyHorizontalPager
import com.superbeta.blibberly_home.presentation.ui.components.HomeScreenShimmerEffect
import com.superbeta.blibberly_home.presentation.viewModel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier,
    navigateToChat: (String, String) -> Unit,
    homeViewModel: HomeViewModel = koinViewModel(),
    navigateToNoUsers: () -> Unit
) {
    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp

    val homeScreenState by homeViewModel.homeScreenState.collectAsStateWithLifecycle()
    val liveUsers by homeViewModel.usersState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope { Dispatchers.IO }
    val liveUserProfile by homeViewModel.userProfileState.collectAsStateWithLifecycle()
    val pagerState = rememberPagerState(pageCount = {
        liveUserProfile.size
    })

    Log.i("HomeScreen state", homeScreenState.toString())

    LaunchedEffect(key1 = true) {
        scope.launch {
            try {
                homeViewModel.getUsers()
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
                homeViewModel.getUserProfile()
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






