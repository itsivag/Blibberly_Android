package com.superbeta.blibberly.onboarding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.TextColorGrey

@Composable
fun FillInTheBlanksDropDown(
    dropDownOptions: List<String>,
    value: String,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = value.ifEmpty { dropDownOptions.first() },
            modifier = Modifier.clickable { expanded = !expanded },
            textDecoration = TextDecoration.Underline,
            color = if (value.isNotEmpty()) TextColorGrey else ColorDisabled,
            style = MaterialTheme.typography.titleLarge,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            repeat(dropDownOptions.size) {
                DropdownMenuItem(
                    text = { Text(dropDownOptions[it], color = TextColorGrey) },
                    onClick = {
                        onValueChange(dropDownOptions[it])
                        expanded = false
                    }
                )
            }
        }
    }
}