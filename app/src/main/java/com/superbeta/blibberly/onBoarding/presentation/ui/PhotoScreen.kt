package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.theme.components.PrimaryButton
import com.superbeta.blibberly.utils.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoScreen(
    modifier: Modifier, navController: NavHostController,
) {

    val isButtonEnabled by remember {
        mutableStateOf(true)
    }
    val scope = rememberCoroutineScope()
    val avatarBGColorsList = listOf(
        Color.Blue,
        Color.White,
        Color.Red,
        Color.Gray,
        Color.Cyan,
        Color.Black,
        Color.DarkGray,
        Color.Green,
        Color.Magenta,
        Color.Yellow
    )

    val avatarBGEmojiList = listOf(
        "😀", "💪🏼", "💀"
    )

    var selectedAvatarBGColor by remember {
        mutableStateOf(avatarBGColorsList[0])
    }


    var selectedAvatarBGEmoji by remember {
        mutableStateOf(avatarBGEmojiList[0])
    }

    LazyColumn {
        item {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "back"
                    )
                }
            })
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(color = selectedAvatarBGColor), contentAlignment = Alignment.Center
            ) {
                Text(text = selectedAvatarBGEmoji)
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.edit),
                    contentDescription = "avatar",
                    contentScale = ContentScale.None,
                )
            }
        }

        item {
            Text(text = "Bg Color", modifier = Modifier.padding(8.dp))
            LazyRow {
                items(count = avatarBGColorsList.size) { color ->
                    Box(modifier = Modifier
                        .padding(10.dp)
                        .size(48.dp)
                        .background(color = avatarBGColorsList[color], shape = CircleShape)
                        .border(
                            width = 2.dp,
                            shape = CircleShape,
                            brush = Brush.linearGradient(listOf(Color.Black, Color.Black))
                        )
                        .clickable {
                            selectedAvatarBGColor = avatarBGColorsList[color]
                        })
                }
            }
        }

        item {
            Text(text = "Bg Emoji", modifier = Modifier.padding(8.dp))
            LazyRow {
                items(count = avatarBGEmojiList.size) { emoji ->
                    Box(
                        modifier = Modifier
                            .padding(10.dp)
                            .size(48.dp)
                            .border(
                                width = 2.dp,
                                shape = CircleShape,
                                brush = Brush.linearGradient(listOf(Color.Black, Color.Black))
                            )
                            .clickable {
                                selectedAvatarBGEmoji = avatarBGEmojiList[emoji]
                            }, contentAlignment = Alignment.Center
                    ) {
                        Text(text = avatarBGEmojiList[emoji])
                    }
                }
            }
        }

        item {
            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                buttonText = "Continue",
                isButtonEnabled = isButtonEnabled
            ) {
                navController.navigate(Screen.CurateProfile.route)
            }
        }
    }
}
