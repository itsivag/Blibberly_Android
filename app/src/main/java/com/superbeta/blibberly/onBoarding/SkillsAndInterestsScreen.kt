package com.superbeta.blibberly.onBoarding

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SkillsAndInterestsScreen(modifier: Modifier, navController: NavHostController) {

    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    var aboutMe by remember {
        mutableStateOf(TextFieldValue())
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

    if (selectedInterests.size == 3)
        isButtonEnabled = true
    else
        isButtonEnabled = false

    Column(modifier = modifier) {
//        Spacer(modifier = Modifier.weight(1f))

        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "back"
                    )
                }
            })

        LazyColumn {
            item {
                Text(
                    text = "Your interests could spark great connections! Your skills might even unlock chats with similar folks!",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp)
                )
            }

            item {


                Text(
                    text = "Share your interests! 🌟",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }


//                LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Adaptive(120.dp)) {
            items(count = interests.size) { i ->
                val currInterest = interests[i]
                val isChecked = remember { mutableStateOf(false) }

                Text(
                    text = currInterest,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(
                            color = if (!isChecked.value) Color.White else ColorPrimary.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .border(
                            width = 1.dp,
                            brush =
                            if (!isChecked.value)
                                Brush.horizontalGradient(listOf(ColorDisabled, ColorDisabled))
                            else
                                Brush.horizontalGradient(listOf(ColorPrimary, ColorPrimary)),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .clickable {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            if (isChecked.value) {
                                selectedInterests.remove(currInterest)
                            } else {
                                if (selectedInterests.size < 3) {
                                    selectedInterests.add(currInterest)
                                }
                            }
                            isChecked.value = !isChecked.value
                            Log.i("interests2", selectedInterests.toString())
                        },
                    style = MaterialTheme.typography.labelLarge,
                )
            }

//                }

            item {

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.End,
                    text = "${selectedInterests.size}/3",
                    style = MaterialTheme.typography.titleLarge,
                    color = if (selectedInterests.size == 3) ColorPrimary else ColorDisabled
                )

            }

            item {

                PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp), buttonText = "Next", isButtonEnabled = isButtonEnabled
                ) {
                    navController.navigate("onboarding")
                }

            }
        }

    }
}