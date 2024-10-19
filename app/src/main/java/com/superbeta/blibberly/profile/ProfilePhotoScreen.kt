package com.superbeta.blibberly.profile

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import coil.request.ImageRequest
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.BLIBMOJI_BG_COLORS
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.ui.components.PrimaryButtonColorDisabled
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
//import com.superbeta.blibberly_supabase.utils.supabase
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val DEFAULT_BLIBMOJI_URL =
    "https://dxyahfscoumjwjuwlgje.supabase.co/storage/v1/object/public/blibmoji/boy_1.webp?t=2024-07-25T07%3A34%3A55.514Z"

@Composable
fun ProfilePhotoScreen(
    navController: NavHostController,
    viewModel: UserViewModel = koinViewModel()
) {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://dxyahfscoumjwjuwlgje.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR4eWFoZnNjb3VtandqdXdsZ2plIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTY5MTUzOTMsImV4cCI6MjAzMjQ5MTM5M30.DqthAS5M1CSeBFQf87TAxv57eMCalxxiPAbRp_XQ8AE"
    ) {
        install(Postgrest)
        install(Auth)
        install(Storage)
    }

    val avatarBGColorsMap = mapOf<String, Color>(
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
    val avatarBgColorList = avatarBGColorsMap.entries.toList()

    val avatarBGEmojiList = listOf(
        "\uD83E\uDD70", "🧁", "🍰", "🎁", "🎂", "🎈", "🎺",
    )

    val isButtonEnabled by rememberSaveable {
        mutableStateOf(true)
    }

    var blibmojiUrlList by rememberSaveable {
        mutableStateOf(listOf<String>())
    }

    var selectedBGColor by rememberSaveable {
        mutableStateOf(BLIBMOJI_BG_COLORS.BLUE.toString())
    }

    var selectedBGEmoji by rememberSaveable {
        mutableStateOf(avatarBGEmojiList[0])
    }

    var selectedBlibmoji by rememberSaveable {
        mutableStateOf(DEFAULT_BLIBMOJI_URL)
    }

    var isEditing by rememberSaveable {
        mutableStateOf(false)
    }

    val config = LocalConfiguration.current
    val screenHeight = config.screenHeightDp.dp
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            try {
                val bucket = supabase.storage.from("blibmoji")
                val files = bucket.list()
                val urls = files.map { bucket.publicUrl(it.name) }
                blibmojiUrlList = urls

                Log.i("Blibmoji's from Storage", urls.toString())

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            viewModel.getUser()
            val userData: UserDataModel? = viewModel.userState.value
            if (userData != null && userData.photoMetaData.blibmojiUrl.isNotEmpty() && userData.photoMetaData.bgEmoji.isNotEmpty() && userData.photoMetaData.bgColor.isNotEmpty()) {
                selectedBlibmoji = userData.photoMetaData.blibmojiUrl
                selectedBGEmoji = userData.photoMetaData.bgEmoji
                selectedBGColor = userData.photoMetaData.bgColor
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        avatarBGColorsMap[selectedBGColor]?.let {
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
                                text = selectedBGEmoji,
//                                fontFamily = FontProvider.notoEmojiFontFamily,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }

                SubcomposeAsyncImage(
                    model = selectedBlibmoji,
                    contentDescription = "",
                ) {
                    val state = painter.state
                    if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
                        CircularProgressIndicator()
                    } else {
                        SubcomposeAsyncImageContent()
                    }
                }

                IconButton(
                    onClick = {
                        isEditing = !isEditing
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .background(color = Color.White, shape = CircleShape)
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.edit),
                        contentDescription = "Edit Blibmoji"
                    )
                }
            }
        }

        AnimatedVisibility(visible = isEditing) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Bg Color", modifier = Modifier.padding(8.dp))
                LazyRow {
                    items(count = avatarBGColorsMap.size) { i ->
                        val (key: String, value: Color) = avatarBgColorList[i]

                        Box(modifier = Modifier
                            .padding(10.dp)
                            .size(48.dp)
                            .background(
                                color = value,
                                shape = CircleShape
                            )
                            .border(
                                width = 2.dp,
                                shape = CircleShape,
                                brush = Brush.linearGradient(
                                    listOf(
                                        Color.Black,
                                        Color.Black
                                    )
                                )
                            )
                            .clickable {
                                selectedBGColor = key
                            })
                    }
                }

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
                                    brush = Brush.linearGradient(
                                        listOf(
                                            Color.Black,
                                            Color.Black
                                        )
                                    )
                                )
                                .clickable {
                                    selectedBGEmoji = avatarBGEmojiList[emoji]
                                }, contentAlignment = Alignment.Center
                        ) {
                            Text(text = avatarBGEmojiList[emoji])
                        }
                    }
                }


                Text(text = "Blibmoji", modifier = Modifier.padding(8.dp))
                LazyRow {
                    items(count = blibmojiUrlList.size) { i ->

                        if (i != 0)
                            Box(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .size(48.dp)
                                    .background(color = Color.White, shape = CircleShape)
                                    .border(
                                        width = 2.dp,
                                        shape = CircleShape,
                                        brush = Brush.linearGradient(
                                            listOf(
                                                Color.Black,
                                                Color.Black
                                            )
                                        )
                                    )
                                    .padding(8.dp)
                                    .clickable {
                                        selectedBlibmoji = blibmojiUrlList[i]
                                    }
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(blibmojiUrlList[i])
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(R.drawable.placeholder),
                                    contentDescription = "Emoji For People",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .fillMaxSize()
                                )

                            }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    PrimaryButtonColorDisabled(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        buttonText = "Cancel",
                        isButtonEnabled = isButtonEnabled
                    ) {
                        isEditing = false
                    }
                    PrimaryButton(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp, vertical = 16.dp),
                        buttonText = "Save",
                        isButtonEnabled = isButtonEnabled
                    ) {
                        scope.launch {
                            viewModel.updatePhotoMetaData(
                                PhotoMetaData(
                                    blibmojiUrl = selectedBlibmoji,
                                    bgEmoji = selectedBGEmoji,
                                    bgColor = selectedBGColor
                                )
                            )
                        }.invokeOnCompletion {
                            isEditing = false
                        }
                    }
                }
            }

        }
    }

}
