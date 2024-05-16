package com.superbeta.blibberly.ui.theme.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.ColorSecondary
import kotlin.reflect.jvm.internal.impl.types.checker.TypeRefinementSupport.Enabled


@Composable
fun TextFieldWithLabel(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    labelText: String,
    placeHolderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isEnabled: Boolean = true
) {
    Text(
        text = labelText,
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    OutlinedTextField(
        enabled = isEnabled,
        maxLines = 1,
        value = textFieldValue,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        onValueChange = onTextFieldValueChange,
        shape = RoundedCornerShape(14.dp),
        placeholder = {
            Text(
                text = placeHolderText,
                color = ColorDisabled,
//                    modifier = Modifier.padding(vertical = 8.dp)
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ColorPrimary,
            unfocusedBorderColor = ColorDisabled,
        ),
        keyboardOptions = keyboardOptions
    )
}


@Composable
fun TextFieldWithTrailingIcon(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    labelText: String,
    placeHolderText: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {

    var isFocused by remember {
        mutableStateOf(false)
    }

    val focusRequester = remember {
        FocusRequester()
    }

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
        TextField(
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
            colors = TextFieldDefaults.colors(
                unfocusedTextColor = ColorDisabled,
                focusedContainerColor = Color.Unspecified,
                disabledContainerColor = Color.Unspecified,
                errorContainerColor = Color.Unspecified,
                unfocusedContainerColor = Color.Unspecified,
                focusedIndicatorColor = Color.Unspecified,
                errorIndicatorColor = Color.Unspecified,
                disabledIndicatorColor = Color.Unspecified,
                unfocusedIndicatorColor = Color.Unspecified
            ),
            keyboardOptions = keyboardOptions
        )

        AnimatedVisibility(visible = isFocused) {

            Row(modifier = Modifier.padding(end = 4.dp)) {
                IconButton(onClick = {
                    focusRequester.freeFocus()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.close),
                        contentDescription = "Clear",
                        tint = Color.DarkGray
                    )
                }
                IconButton(onClick = {
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
                focusRequester.requestFocus()
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
