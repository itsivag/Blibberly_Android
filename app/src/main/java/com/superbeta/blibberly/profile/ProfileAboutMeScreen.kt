package com.superbeta.blibberly.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import com.superbeta.blibberly.R
import com.superbeta.blibberly.user.data.UserLocalDbService
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileAboutMeScreen(
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = UserViewModel.Factory)
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

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            val userData: UserDataModel? =
                viewModel.userState.value
            if (userData != null) {
                aboutMe = TextFieldValue(userData.aboutMe)
            }
        }
    }


    val focusManager = LocalFocusManager.current

    Column {
        HorizontalDivider(color = ColorDisabled, modifier = Modifier.padding(bottom = 16.dp))

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
            placeholder = {
                Text(
                    text = "Yo Blibbers! \uD83C\uDF1F I'm Mia, a coffee aficionado ☕ and aspiring travel blogger \uD83C\uDF0D. Catch me vibing to indie tunes or geeking out over the latest tech trends. Let's swap memes, share playlists, and see if our stars align on Blibberly! Let's make some memories, fam! \uD83D\uDE80✨\"",
                    color = ColorDisabled,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            },
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
//                                    updateValueInRoom()
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
    }
}