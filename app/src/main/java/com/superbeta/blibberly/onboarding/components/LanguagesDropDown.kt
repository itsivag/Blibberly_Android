package com.superbeta.blibberly.onboarding.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.ColorPrimary
import com.superbeta.blibberly.utils.FontProvider


@Composable
fun LanguagesDropDown(selectedValue: SnapshotStateList<String>) {
    val languages = listOf(
        "Amharic",
        "Arabic",
        "Azerbaijani",
        "Bengali",
        "Burmese",
        "Cantonese",
        "Czech",
        "Dutch",
        "English",
        "Farsi (Persian)",
        "French",
        "Fula",
        "German",
        "Greek",
        "Gujarati",
        "Hausa",
        "Hebrew",
        "Hindi",
        "Hungarian",
        "Igbo",
        "Indonesian",
        "Italian",
        "Japanese",
        "Javanese",
        "Kannada",
        "Kazakh",
        "Khmer",
        "Korean",
        "Lao",
        "Malay",
        "Malayalam",
        "Malagasy",
        "Mandarin Chinese",
        "Marathi",
        "Mongolian",
        "Nepali",
        "Pashto",
        "Polish",
        "Portuguese",
        "Punjabi",
        "Romanian",
        "Russian",
        "Sanskrit",
        "Santali",
        "Shona",
        "Sinhala",
        "Sindhi",
        "Somali",
        "Spanish",
        "Swahili",
        "Swedish",
        "Tagalog (Filipino)",
        "Tamil",
        "Telugu",
        "Thai",
        "Tibetan",
        "Tigrinya",
        "Turkish",
        "Ukrainian",
        "Urdu",
        "Uzbek",
        "Vietnamese",
        "Xhosa",
        "Yoruba",
        "Zulu"
    )


    val context = LocalContext.current
    DynamicSelectTextField(
        selectedValue = selectedValue.lastOrNull() ?: "",
        options = languages,
        label = "Language",
        onValueChangedEvent = {
            if (!selectedValue.contains(it) && selectedValue.size < 5) {
                selectedValue.add(it)
            } else {
                Toast.makeText(context, "You can add at most 5 languages.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    )


    LazyRow(modifier = Modifier.padding(horizontal = 12.dp)) {
        items(selectedValue.size) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .border(
                        width = 1.dp,
                        color = ColorPrimary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp)
            ) {
                Text(
                    text = selectedValue[it],
                    fontFamily = FontProvider.dmSansFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.close),
                    contentDescription = "close",
                    modifier = Modifier.clickable { selectedValue.remove(selectedValue[it]) }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicSelectTextField(
    selectedValue: String,
    options: List<String>,
    label: String,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Text(
        text = "I can speak",
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
//            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryEditable, true)
                .padding(16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(
                    text = "Languages",
                    color = ColorDisabled,
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ColorPrimary,
                unfocusedBorderColor = ColorDisabled,
            ),
        )

        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option: String ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}
