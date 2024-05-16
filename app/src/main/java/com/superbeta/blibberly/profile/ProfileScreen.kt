package com.superbeta.blibberly.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavHostController
import com.superbeta.blibberly.onBoarding.data.UserLocalDbService
import com.superbeta.blibberly.onBoarding.data.model.UserDataModel
import com.superbeta.blibberly.ui.theme.components.TextFieldWithLabel
import com.superbeta.blibberly.ui.theme.components.TextFieldWithTrailingIcon
import com.superbeta.blibberly.utils.RoomInstanceProvider
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(modifier: Modifier, navController: NavHostController) {

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

    var isEnabled by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            val userData: UserDataModel? =
                UserLocalDbService(RoomInstanceProvider.getDb(context)).getUser()
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

    LazyColumn(modifier = modifier) {
        item {
            TextFieldWithTrailingIcon(
                textFieldValue = name,
                onTextFieldValueChange = { value -> name = value },
                "Name",
                "Sankar",
            )
        }

        item {
            TextFieldWithTrailingIcon(
                textFieldValue = age,
                onTextFieldValueChange = { value -> age = value },
                "Age",
                "22",
            )
        }


        item {
            TextFieldWithTrailingIcon(
                textFieldValue = height,
                onTextFieldValueChange = { value -> height = value },
                "Height",
                "180",
            )
        }

        item {
            TextFieldWithTrailingIcon(
                textFieldValue = weight,
                onTextFieldValueChange = { value -> weight = value },
                "Weight",
                "75",
            )
        }
    }
}