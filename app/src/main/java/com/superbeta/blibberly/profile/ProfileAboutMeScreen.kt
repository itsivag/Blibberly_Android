package com.superbeta.blibberly.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.components.PrimaryButtonColorDisabled
import com.superbeta.blibberly_auth.theme.ColorDisabled
import com.superbeta.blibberly_auth.theme.ColorPrimary
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileAboutMeScreen(
    userViewModel: UserViewModel = koinViewModel(),
    authViewModel: AuthViewModel = koinViewModel()
) {

    var aboutMe by remember {
        mutableStateOf(TextFieldValue())
    }

    var isFocused by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember {
        FocusRequester()
    }

    val userData by userViewModel.userState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        scope.launch(IO) {
            userViewModel.getUser()
        }
    }

    LaunchedEffect(key1 = userData) {
        scope.launch(IO) {
            try {
                aboutMe = TextFieldValue(userData!!.aboutMe)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    val focusManager = LocalFocusManager.current

    Column {
//        HorizontalDivider(color = ColorDisabled, modifier = Modifier.padding(bottom = 16.dp))

        Text(
            text = "About Me",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(minLines = 3,
            value = aboutMe,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .focusRequester(focusRequester)
                .onFocusChanged { isFocused = it.hasFocus },
            onValueChange = { value -> aboutMe = value },
            shape = RoundedCornerShape(14.dp),
//            placeholder = {
//                Text(
//                    text = "Yo Blibbers! \uD83C\uDF1F I'm Mia, a coffee aficionado ☕ and aspiring travel blogger \uD83C\uDF0D. Catch me vibing to indie tunes or geeking out over the latest tech trends. Let's swap memes, share playlists, and see if our stars align on Blibberly! Let's make some memories, fam! \uD83D\uDE80✨\"",
//                    color = ColorDisabled,
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ColorPrimary,
                unfocusedBorderColor = ColorDisabled,
            ),
            trailingIcon = {
                AnimatedVisibility(visible = isFocused) {

                    Row(modifier = Modifier.padding(end = 4.dp)) {
                        IconButton(onClick = {
                            scope.launch {
                                focusManager.clearFocus()
                            }
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.close),
                                contentDescription = "Clear",
                                tint = Color.DarkGray
                            )
                        }
                        IconButton(onClick = {
                            scope.launch {
                                userViewModel.updateAboutMe(aboutMe.text)
                            }.invokeOnCompletion {
                                focusManager.clearFocus()
                            }
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.tick),
                                contentDescription = "Confirm",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                AnimatedVisibility(visible = !isFocused) {
                    IconButton(onClick = {
                        scope.launch {
                            focusRequester.requestFocus()
                        }
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.edit),
                            contentDescription = "Edit",
                            tint = Color.Black
                        )
                    }
                }
            })

        PrimaryButtonColorDisabled(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            buttonText = "Log out",
            isButtonEnabled = true
        ) {
            scope.launch {
                userViewModel.deleteLocalUserInfo()
                authViewModel.logOut()
            }
        }

        PrimaryButtonColorDisabled(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            buttonText = "Delete Account",
            isButtonEnabled = true
        ) {
//            viewModel.deleteAccount()
        }
    }
}