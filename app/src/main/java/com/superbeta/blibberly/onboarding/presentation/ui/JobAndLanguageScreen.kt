package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.ColorPrimary
import com.superbeta.blibberly.ui.TextColorGrey
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly_home.utils.FontProvider
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JobAndLanguageScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    navigateToPhoto: () -> Unit,
    viewModel: UserViewModel = koinViewModel()
) {
    var workingAt by remember {
        mutableStateOf(TextFieldValue())
    }

    var studyOrWork by remember {
        mutableStateOf("")
    }

    Column(modifier.fillMaxSize()) {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "back"
                    )
                }
            })

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "What do you do for a living?",
            color = TextColorGrey,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "I",
                color = TextColorGrey,
                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(16.dp)
            )
            StudyOrWorkDropDown(
                studyOrWork,
                editStudyOrWork = { value: String -> studyOrWork = value })
            Text(
                text = "at",
                color = TextColorGrey,
                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(16.dp)
            )
            TextField(
                value = workingAt,
                textStyle = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    fontFamily = FontProvider.poppinsFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    lineHeight = 28.sp,
                    color = TextColorGrey
                ),
                placeholder = {
                    Text(
                        style = MaterialTheme.typography.titleLarge,
                        text = "Google",
                        color = ColorDisabled,
                        textDecoration = TextDecoration.Underline
                    )
                },
                onValueChange = { workingAt = it },
                colors = TextFieldDefaults.colors(
                    focusedSupportingTextColor = TextColorGrey,
                    errorSupportingTextColor = TextColorGrey,
                    disabledSupportingTextColor = TextColorGrey,
                    unfocusedSupportingTextColor = TextColorGrey,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

        }

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            buttonText = "Continue",
            buttonContainerColor = ColorPrimary,
            isButtonEnabled = studyOrWork.isNotEmpty() && workingAt.text.isNotEmpty()
        ) {
            navigateToPhoto()
        }

//        TextFieldWithLabel(
//            textFieldValue = studyOrWork,
//            onTextFieldValueChange = { value -> studyOrWork = value },
//            "Name",
//            "Mega",
//            keyboardOptions = KeyboardOptions(),
//            isError = studyOrWork.text.isNotEmpty(),
//            errorText = "name should be at least 4 characters"
//        )

    }
}

@Composable
fun StudyOrWorkDropDown(studyOrWork: String, editStudyOrWork: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    val options = listOf(
        "Work", "Own", "Study", "Freelance", "Intern", "Hustle", "Create"
    )
    Box(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = studyOrWork.ifEmpty { "Dance" },
            modifier = Modifier.clickable { expanded = !expanded },
            textDecoration = TextDecoration.Underline,
            color = if (studyOrWork.isNotEmpty()) TextColorGrey else ColorDisabled,
            style = MaterialTheme.typography.titleLarge,
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            repeat(options.size) {
                DropdownMenuItem(
//                    colors = MenuItemColors(),
                    text = { Text(options[it], color = TextColorGrey) },
                    onClick = {
                        editStudyOrWork(options[it])
//                        selectedText = options[it]
                        expanded = false
                    }
                )
            }
//            DropdownMenuItem(
//                text = { Text("Option 2") },
//                onClick = { /* Do something... */ }
//            )
        }
    }
}