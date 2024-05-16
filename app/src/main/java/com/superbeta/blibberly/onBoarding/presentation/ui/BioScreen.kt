package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.onBoarding.data.UserLocalDbService
import com.superbeta.blibberly.onBoarding.data.model.UserDataModel
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.components.PrimaryButton
import com.superbeta.blibberly.ui.theme.components.TextFieldWithLabel
import com.superbeta.blibberly.utils.BlibberlyDatabase
import com.superbeta.blibberly.utils.RoomInstanceProvider
import kotlinx.coroutines.launch

enum class HeightUnit {
    Cm, Inch,
}


enum class WeightUnit {
    Kg, Lbs,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BioScreen(modifier: Modifier, navController: NavController) {
    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

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


    val scope = rememberCoroutineScope()
    val context = LocalContext.current

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

    isButtonEnabled = true
//        !(name.text.isEmpty() || age.text.isEmpty() || height.text.isEmpty() || weight.text.isEmpty())

    Column(modifier = modifier) {
        TopAppBar(title = { }, navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                    contentDescription = "back"
                )
            }
        })

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Sharpen your profile! Add your 'Vitals' to stand out from the crowd and make a lasting impression!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        )

        Text(
            text = "\nYour Vitals\uD83C\uDF1F",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        TextFieldWithLabel(
            textFieldValue = name,
            onTextFieldValueChange = { value -> name = value },
            "Name",
            "Sankar",
        )


        TextFieldWithLabel(
            textFieldValue = age,
            onTextFieldValueChange = { value -> age = value },
            "Age",
            "22",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

        )

        Row {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Height",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                OutlinedTextField(
                    maxLines = 1,
                    value = height,
                    modifier = Modifier.padding(16.dp),
                    onValueChange = { value -> height = value },
                    shape = RoundedCornerShape(14.dp),
                    placeholder = {
                        Text(
                            text = "180",
                            color = ColorDisabled,
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ColorPrimary,
                        unfocusedBorderColor = ColorDisabled,
                    ),
                    trailingIcon = {
                        Text(
                            text = "cm",
                            color = ColorDisabled,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },

                    )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Weight",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                OutlinedTextField(maxLines = 1,
                    value = weight,
                    modifier = Modifier.padding(16.dp),
                    onValueChange = { value -> weight = value },
                    shape = RoundedCornerShape(14.dp),
                    placeholder = {
                        Text(
                            text = "80",
                            color = ColorDisabled,
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = ColorPrimary,
                        unfocusedBorderColor = ColorDisabled,
                    ),
                    trailingIcon = {
                        Text(
                            text = "kg",
                            color = ColorDisabled,
                            style = MaterialTheme.typography.labelLarge
                        )
                    })
            }
        }

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            buttonText = "Next",
            isButtonEnabled = isButtonEnabled
        ) {

            scope.launch {
                UserLocalDbService(RoomInstanceProvider.getDb(context)).setUSer(
                    UserDataModel(
                        phoneNum = "88383428234",
                        name = name.text,
                        age = age.text.toInt(),
                        height = height.text.toDouble(),
                        weight = weight.text.toDouble(),
                        aboutMe = "sample",
                        interests = listOf("one", "two", "three")
                    )
                )
            }.invokeOnCompletion {
                navController.navigate("about_me")
            }
        }
    }

}
