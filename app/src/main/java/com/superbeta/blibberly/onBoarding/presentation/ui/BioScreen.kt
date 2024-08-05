package com.superbeta.blibberly.onBoarding.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.components.PrimaryButton
import com.superbeta.blibberly.ui.theme.components.TextFieldWithLabel
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.utils.Screen
import kotlinx.coroutines.launch

enum class HeightUnit {
    Cm, Inch,
}


enum class WeightUnit {
    Kg, Lbs,
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BioScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: UserViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = UserViewModel.Factory)
) {

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

    var aboutMe by remember {
        mutableStateOf("")
    }

    var photoUri by remember {
        mutableStateOf("")
    }

    val interests = remember {
        mutableStateListOf<String>()
    }

    var gender by remember {
        mutableStateOf("")
    }
    var userFCMToken by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val userData = viewModel.userState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        scope.launch {
            viewModel.getUser()
        }
    }

    LaunchedEffect(key1 = userData) {
        scope.launch {
            if (userData != null) {
                name = TextFieldValue(userData.name)
                age = TextFieldValue(userData.age.toString())
                height = TextFieldValue(userData.height.toString())
                weight = TextFieldValue(userData.weight.toString())
                aboutMe = userData.aboutMe
                interests.addAll(userData.interests)
//                photoUri = userData.photoMetaData
                gender = userData.gender
            }
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            userFCMToken = viewModel.getUserFCMToken()
        }
    }

    isButtonEnabled = true
//        !(name.text.isEmpty() || age.text.isEmpty() || height.text.isEmpty() || weight.text.isEmpty())

    LazyColumn(modifier = modifier) {
        item {

            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "back"
                    )
                }
            })
        }


//        Spacer(modifier = Modifier.weight(1f))

        item {

            Text(
                text = "Sharpen your profile! Add your 'Vitals' to stand out from the crowd and make a lasting impression!",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.secondary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(16.dp)
            )

        }
        item {

            Text(
                text = "\nYour Vitals\uD83C\uDF1F",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

        item {

            TextFieldWithLabel(
                textFieldValue = name,
                onTextFieldValueChange = { value -> name = value },
                "Name",
                "Sankar",
                keyboardOptions = KeyboardOptions()
            )
        }

        item {

            TextFieldWithLabel(
                textFieldValue = age,
                onTextFieldValueChange = { value -> age = value },
                "Age",
                "22",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )
        }

        item {

            Text(
                text = "Gender",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp)
            ) {
                IconButton(modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .border(
                        width = 1.dp,
                        color = if (gender == "F") ColorPrimary else ColorDisabled,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp), onClick = { gender = "F" }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.female),
                        contentDescription = "Female"
                    )
                }

                IconButton(modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .border(
                        width = 1.dp,
                        color = if (gender == "M") ColorPrimary else ColorDisabled,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp), onClick = { gender = "M" }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.male),
                        contentDescription = "Male"
                    )
                }
            }
        }
        item {

            Row(modifier = Modifier.padding(vertical = 8.dp)) {
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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
        }

        item {

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                buttonText = "Next",
                isButtonEnabled = isButtonEnabled
            ) {
                scope.launch {
                    try {
                        viewModel.setUser(
                            UserDataModel(
                                email = "88383428234",
                                name = name.text,
                                age = age.text.toInt(),
                                height = height.text.toDouble(),
                                weight = weight.text.toDouble(),
                                aboutMe = aboutMe,
                                interests = interests,
                                gender = gender,
                                photoMetaData = PhotoMetaData("", "", ""),
                                fcmToken = userFCMToken
                            )
                        )

                    } catch (e: Exception) {
                        Log.e("Error Storing data in room BioScreen", e.toString())
                    }
                }.invokeOnCompletion {
                    navController.navigate(Screen.AboutMe.route)
                }
            }
        }
    }

}
