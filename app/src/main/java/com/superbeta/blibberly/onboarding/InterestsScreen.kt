package com.superbeta.blibberly.onboarding

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.Gson
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.ColorPrimary
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly_auth.model.UserDataModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestsScreen(
    modifier: Modifier,
//    navController: NavHostController,
    navigateBack: () -> Unit,
    navigateToJobAndLanguage: () -> Unit,
    viewModel: UserViewModel = koinViewModel()
) {

    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    val selectedInterests = remember {
        mutableStateListOf<String>()
    }

    val interests = listOf(
        "🍳 Cooking",
        "🎮 Gaming",
        "📸 Photography",
        "✈️ Traveling",
        "📚 Reading",
        "🥾 Hiking",
        "🧘 Yoga",
        "🎨 Painting",
        "🎵 Music",
        "✍️ Writing",
        "🎬 Film and TV Series",
        "💪 Fitness",
        "👗 Fashion",
        "🌿 Gardening",
        "📱 Technology",
        "🐾 Pets",
        "🛠️ DIY",
        "⚽ Sports",
        "🧘 Meditation and Mindfulness",
        "♻️ Sustainable Living"
    )


    val haptic = LocalHapticFeedback.current

    isButtonEnabled = selectedInterests.size == 3

    val scope = rememberCoroutineScope()
    val userData: UserDataModel? = viewModel.userState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.getUser()
        }
    }

    LaunchedEffect(key1 = userData) {
        scope.launch {
            if (userData != null) {
                try {

                    val interestsList: Array<String>? =
                        Gson().fromJson(userData.interests, Array<String>::class.java)
                    if (interestsList != null) {
                        for (i in interestsList) {
                            if (i.isNotEmpty()) {
                                selectedInterests.add(i)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    Column(modifier = modifier) {
//        item {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "back"
                    )
                }
            })

//        }
//        item {
//        Text(
//            text = "Your interests could spark great connections! Your skills might even unlock chats with similar folks!",
//            style = MaterialTheme.typography.bodyMedium,
//            modifier = Modifier
//                .padding(16.dp)
//                .background(
//                    color = MaterialTheme.colorScheme.secondary,
//                    shape = RoundedCornerShape(12.dp)
//                )
//                .padding(16.dp)
//        )
//        }

//        item {
        Text(
            text = "Share your interests! 🌟",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
//        }


//        item {
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Adaptive(minSize = 52.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(count = interests.size) { i ->
                val currInterest = interests[i]

                Text(
                    text = currInterest,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = if (!selectedInterests.contains(currInterest)) Color.White else ColorPrimary.copy(
                                alpha = 0.3f
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 1.dp,
                            brush =
                            if (!selectedInterests.contains(currInterest))
                                Brush.horizontalGradient(listOf(ColorDisabled, ColorDisabled))
                            else
                                Brush.horizontalGradient(listOf(ColorPrimary, ColorPrimary)),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .clickable {
                            if (selectedInterests.contains(currInterest)) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedInterests.remove(currInterest)
                            } else {
                                if (selectedInterests.size < 3) {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    selectedInterests.add(currInterest)
                                }
                            }
//                        isChecked.value = !isChecked.value
                            Log.i("interests2", selectedInterests.toString())
                        },
                    style = MaterialTheme.typography.labelLarge,
                )
            }

        }
//        }

//        item {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.End,
            text = "${selectedInterests.size}/3",
            style = MaterialTheme.typography.titleLarge,
            color = if (selectedInterests.size == 3) ColorPrimary else ColorDisabled
        )

//        }

//        item {
        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            buttonText = "Continue",
            isButtonEnabled = isButtonEnabled,
            onClickMethod = {
                scope.launch {
                    viewModel.updateInterests(selectedInterests)
                }.invokeOnCompletion {
                    navigateToJobAndLanguage()
                }
            })

    }
//    }

}
