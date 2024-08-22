package com.superbeta.blibberly_auth.theme.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary


@Composable
fun TextFieldWithLabel(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    labelText: String,
    placeHolderText: String,
    keyboardOptions: KeyboardOptions,
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
