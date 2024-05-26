package com.superbeta.blibberly_chat.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_chat.R

@Preview(showBackground = true)
@Composable
fun MessageTextField() {
    var message by remember {
        mutableStateOf(TextFieldValue())
    }

    val textFieldContainerColor = MaterialTheme.colorScheme.primaryContainer
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        ) {

        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value = message,
            onValueChange = { value -> message = value },
            shape = RoundedCornerShape(24.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Unspecified,
                errorBorderColor = Color.Unspecified,
                focusedBorderColor = Color.Unspecified,
                disabledBorderColor = Color.Unspecified,
                focusedContainerColor = textFieldContainerColor,
                disabledContainerColor = textFieldContainerColor,
                errorContainerColor = textFieldContainerColor,
                unfocusedContainerColor = textFieldContainerColor
            )
        )
        IconButton(
            onClick = { /*TODO*/ },
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(48.dp)
                .padding(start = 4.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.chat),
                contentDescription = "Send Message",
            )
        }
    }

}