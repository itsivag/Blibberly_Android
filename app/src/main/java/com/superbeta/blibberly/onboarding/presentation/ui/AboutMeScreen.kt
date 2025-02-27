package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorPrimary
//import com.superbeta.blibberly_auth.theme.ColorDisabled
//import com.superbeta.blibberly_auth.theme.ColorPrimary
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.utils.Screen
import com.superbeta.blibberly_auth.theme.ColorDisabled
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen(
    modifier: Modifier, navController: NavHostController,
    viewModel: UserViewModel = koinViewModel()
) {
    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    var aboutMe by remember {
        mutableStateOf(TextFieldValue())
    }

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
                aboutMe = TextFieldValue(userData.aboutMe)
            }
        }
    }

    LaunchedEffect(key1 = aboutMe) {
        scope.launch {
            isButtonEnabled = aboutMe.text.isNotEmpty() && aboutMe.text.length >= 50
        }
    }

    Column(modifier = modifier) {

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

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Add an 'About Me' to let others get to know you better. Your story matters on Blibberly!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        )

        Text(
            text = "\nIntroduce yourself! \uD83C\uDF1F",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            isError = aboutMe.text.isNotEmpty() && (aboutMe.text.length < 100),
            supportingText = {
                if (aboutMe.text.isNotEmpty() && (aboutMe.text.length < 100))
                    Text(text = "about me must be at least 50 chars", color = Color.Red) else Text(
                    ""
                )
            },
            minLines = 3,
            value = aboutMe,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onValueChange = { value -> aboutMe = value },
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(
                    text = "Yo Blibbers! \uD83C\uDF1F I'm Mia, a coffee aficionado ☕ and aspiring travel blogger \uD83C\uDF0D. Catch me vibing to indie tunes or geeking out over the latest tech trends. Let's swap memes, share playlists, and see if our stars align on Blibberly! Let's make some memories, fam! \uD83D\uDE80✨\"",
                    color = ColorDisabled,
//                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ColorPrimary,
                unfocusedBorderColor = ColorDisabled,
            )
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            buttonText = "Next",
            isButtonEnabled = isButtonEnabled,
            onClickMethod = {
                scope.launch {
                    viewModel.updateAboutMe(aboutMe.text)
                }.invokeOnCompletion {
                    navController.navigate(Screen.Interests.route)
                }
            }
        )
    }

}