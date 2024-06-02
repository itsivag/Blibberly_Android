package com.superbeta.blibberly.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.superbeta.blibberly.ui.theme.ColorDisabled
import kotlinx.coroutines.launch

@Composable
fun TextFieldWithTrailingIcon(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    labelText: String,
    placeHolderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    updateValueInRoom: () -> Unit
) {

    val scope = rememberCoroutineScope()

    var isFocused by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember {
        FocusRequester()
    }

    val focusManager = LocalFocusManager.current

    Text(
        text = labelText,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(horizontal = 16.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier =
        if (isFocused) {
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(14.dp)
                )
        } else {
            Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = ColorDisabled,
                    shape = RoundedCornerShape(14.dp)
                )
        }
    ) {
        OutlinedTextField(
            maxLines = 1,
            value = textFieldValue,
            onValueChange = onTextFieldValueChange,
            modifier = Modifier
                .weight(1f)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.hasFocus
                },
            placeholder = {
                Text(
                    text = placeHolderText,
                    color = ColorDisabled,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent
            ),
            keyboardOptions = keyboardOptions
        )

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
                        updateValueInRoom()
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
    }
}