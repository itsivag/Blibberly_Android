package com.superbeta.blibberly.onBoarding.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.gson.Gson
import com.superbeta.blibberly.R
import com.superbeta.blibberly_auth.theme.ColorDisabled
import com.superbeta.blibberly_auth.theme.ColorPrimary
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.ui.components.TextFieldWithLabel
import com.superbeta.blibberly.user.data.model.PhotoMetaData
import com.superbeta.blibberly.user.data.model.UserDataModel
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.utils.Screen
//import com.superbeta.blibberly_supabase.utils.supabase
import io.github.jan.supabase.gotrue.auth
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BioScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    navigateToAboutMe: () -> Unit,
    userViewModel: UserViewModel = koinViewModel()
) {

//    var isButtonEnabled by remember {
//        mutableStateOf(false)
//    }


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

    var interests by remember {
        mutableStateOf("")
    }

    var gender by remember {
        mutableStateOf("")
    }
    var userFCMToken by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val userData = userViewModel.userState.collectAsStateWithLifecycle().value

    LaunchedEffect(key1 = true) {
        scope.launch {
            userViewModel.getUser()
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
                interests = Gson().toJson(userData.interests)
                gender = userData.gender
            }
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            userFCMToken = userViewModel.getUserFCMToken()
        }
    }
//    LaunchedEffect(name.text, age.text, height.text, weight.text) {
//        isButtonEnabled = (name.text.isNotEmpty() ||
//                age.text.isNotEmpty() ||
//                height.text.isNotEmpty() ||
//                weight.text.isNotEmpty())
//    }
    val isButtonEnabled by remember(name.text, age.text, height.text, weight.text) {
        mutableStateOf(
            name.text.isNotEmpty() &&
                    age.text.isNotEmpty() &&
                    height.text.isNotEmpty() &&
                    weight.text.isNotEmpty()
        )
    }


    LazyColumn(modifier = modifier.imePadding()) {
        item {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { navigateBack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "back"
                    )
                }
            })
        }

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
                "Mega",
                keyboardOptions = KeyboardOptions(),
                isError = name.text.isNotEmpty() && name.text.length < 4,
                errorText = "name should be at least 4 characters"
            )
        }

        item {
            TextFieldWithLabel(
                textFieldValue = age,
                onTextFieldValueChange = { value -> age = value },
                "Age",
                "22",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                isError = age.text.isNotEmpty() && (age.text.toInt() < 18 || age.text.toInt() > 110),
                errorText = "you should be at least 18 years old"
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
                        width = if (gender == "F") 2.dp else 1.dp,
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
                        width = if (gender == "M") 2.dp else 1.dp,
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
                        isError = height.text.isNotEmpty() && (height.text.toDouble() < 0),
                        supportingText = {
                            if (height.text.isNotEmpty() && (height.text.toDouble() < 0)) Text(
                                text = "Enter a logical height",
                                color = Color.Red
                            ) else Text(text = "")
                        },
                        maxLines = 1,
                        value = height,
                        modifier = Modifier.padding(16.dp),
                        onValueChange = { value -> height = value },
                        shape = RoundedCornerShape(14.dp),
                        placeholder = {
                            Text(
                                text = "175",
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
                        isError = weight.text.isNotEmpty() && (weight.text.toDouble() < 0),
                        supportingText = {
                            if (weight.text.isNotEmpty() && (weight.text.toDouble() < 0)) Text(
                                text = "Enter a logical weight",
                                color = Color.Red
                            ) else Text(text = "")
                        },
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
                isButtonEnabled = isButtonEnabled, onClickMethod = {

                    scope.launch {
                        try {
                            userViewModel.getUserEmail()?.let { email ->
                                UserDataModel(
                                    email = email,
                                    name = name.text,
                                    age = age.text.toInt(),
                                    height = height.text.toDouble(),
                                    weight = weight.text.toDouble(),
                                    aboutMe = aboutMe,
                                    interests = interests,
                                    gender = gender,
                                    photoMetaData = PhotoMetaData("", "", ""),
                                    fcmToken = userFCMToken
                                ).let {
                                    userViewModel.setUser(
                                        it
                                    )
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("Error Storing data in room BioScreen", e.toString())
                        }
                    }.invokeOnCompletion {
                        navigateToAboutMe()
                    }
                })
        }
    }

}
