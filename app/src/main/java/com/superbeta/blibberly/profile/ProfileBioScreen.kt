package com.superbeta.blibberly.profile

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.superbeta.blibberly_auth.theme.ColorDisabled
import com.superbeta.blibberly.ui.components.TextFieldWithTrailingIcon
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileBioScreen(
    viewModel: UserViewModel = koinViewModel()
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var name by remember {
        mutableStateOf(TextFieldValue())
    }

    var age by remember {
        mutableStateOf(TextFieldValue())
    }

    var height by remember {
        mutableStateOf(TextFieldValue())
    }

    var weight by remember {
        mutableStateOf(TextFieldValue())
    }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            viewModel.getUser()
            val userData: UserDataModel? =
                viewModel.userState.value
            if (userData != null) {
                name = TextFieldValue(userData.name)
            }
            if (userData != null) {
                age = TextFieldValue(userData.age.toString())
            }
            if (userData != null) {
                height = TextFieldValue(userData.height.toString())
            }
            if (userData != null) {
                weight = TextFieldValue(userData.weight.toString())
            }
        }
    }

    Column {

        HorizontalDivider(color = ColorDisabled, modifier = Modifier.padding(bottom = 16.dp))
        Text(
            text = "Vitals",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        TextFieldWithTrailingIcon(
            textFieldValue = name,
            onTextFieldValueChange = { value -> name = value },
            "Name",
            "Sankar",
            updateValueInRoom = {
                scope.launch {
                    viewModel.updateName("nottu")
                }
            }
        )

        TextFieldWithTrailingIcon(
            textFieldValue = age,
            onTextFieldValueChange = { value -> age = value },
            "Age",
            "22",
            updateValueInRoom = {
                scope.launch {
                    try {
//                        userLocalDbService.updateAge(age.text.toInt())
                    } catch (e: Exception) {
                        Log.e("Profile Data Store Error", e.toString())
                    }
                }
            }
        )


        TextFieldWithTrailingIcon(
            textFieldValue = height,
            onTextFieldValueChange = { value -> height = value },
            "Height",
            "180",
            updateValueInRoom = {
                scope.launch {
                    try {
//                        userLocalDbService.updateHeight(height.text.toDouble())
                    } catch (e: Exception) {
                        Log.e("Profile Data Store Error", e.toString())
                    }
                }
            }
        )

        TextFieldWithTrailingIcon(
            textFieldValue = weight,
            onTextFieldValueChange = { value -> weight = value },
            "Weight",
            "75",
            updateValueInRoom = {
                scope.launch {
                    try {
//                        userLocalDbService.updateWeight(weight.text.toDouble())
                    } catch (e: Exception) {
                        Log.e("Profile Data Store Error", e.toString())
                    }
                }
            }
        )
    }
}