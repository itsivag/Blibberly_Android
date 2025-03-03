package com.superbeta.blibberly.onboarding

import android.util.Log
import androidx.compose.foundation.border
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
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
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
import com.google.gson.Gson
import com.superbeta.blibberly.R
import com.superbeta.blibberly.onboarding.components.DatePickerDocked
import com.superbeta.blibberly.onboarding.components.LanguagesDropDown
import com.superbeta.blibberly.onboarding.components.convertDateToMillis
import com.superbeta.blibberly.onboarding.components.convertMillisToDate
import com.superbeta.blibberly.onboarding.viewModel.OnBoardingViewModel
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.ColorPrimary
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.ui.components.TextFieldWithLabel
import com.superbeta.blibberly_auth.model.Grind
import com.superbeta.blibberly_auth.model.PhotoMetaData
import com.superbeta.blibberly_auth.model.UserDataModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BioScreen(
    modifier: Modifier,
    navigateBack: () -> Unit,
    navigateToAboutMe: () -> Unit,
    viewModel: OnBoardingViewModel = koinViewModel()
) {

    var name by remember {
        mutableStateOf(TextFieldValue())
    }

    var aboutMe by remember {
        mutableStateOf("")
    }

    var interests by remember {
        mutableStateOf("")
    }

    var gender by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf(TextFieldValue())
    }

    var userFCMToken by remember {
        mutableStateOf("")
    }
    val languages = remember {
        mutableStateListOf<String>()
    }

    val calendar = Calendar.getInstance(Locale.getDefault())
    calendar.add(Calendar.YEAR, -18)
    val minSelectableDate = calendar.timeInMillis

    val datePickerState = rememberDatePickerState(
        yearRange = IntRange(
            1900,
            Calendar.getInstance().get(Calendar.YEAR),
        ),
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= minSelectableDate
            }
        }
    )

    val dob by remember {
        derivedStateOf {
            datePickerState.selectedDateMillis?.let {
                convertMillisToDate(it)
            } ?: ""
        }
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
                datePickerState.selectedDateMillis = convertDateToMillis(userData.dob)
                location = TextFieldValue(userData.location)
                aboutMe = userData.aboutMe
                interests = Gson().toJson(userData.interests)
                gender = userData.gender
                userData.languages.forEach {
                    languages.add(it)
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        scope.launch {
            userFCMToken = viewModel.getUserFCMToken()
        }
    }

    val isButtonEnabled by remember {
        derivedStateOf {
            name.text.isNotEmpty() && name.text.length >= 4 && dob.isNotEmpty() && gender.isNotEmpty() && location.text.isNotEmpty() && location.text.length > 3 && languages.isNotEmpty()
        }
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
            DatePickerDocked(dob, datePickerState)
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
            TextFieldWithLabel(
                textFieldValue = location,
                onTextFieldValueChange = { value -> location = value },
                "Location",
                "Chennai, Tamil Nadu",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                isError = location.text.isNotEmpty() && location.text.length < 3,
                errorText = "location must be valid"
            )
        }

        item {
            LanguagesDropDown(languages)
        }


        item {
            PrimaryButton(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
                buttonText = "Next",
                isButtonEnabled = isButtonEnabled,
                onClickMethod = {
                    scope.launch {
                        try {
                            viewModel.getUserEmail()?.let { email ->
                                UserDataModel(
                                    email = email,
                                    name = name.text,
                                    dob = dob,
                                    aboutMe = aboutMe,
                                    interests = interests,
                                    gender = gender,
                                    photoMetaData = PhotoMetaData("", "", ""),
                                    location = location.text,
                                    grind = Grind("", ""),
                                    languages = languages,
                                    icebreaker = "",
                                    karmaPoint = 0.0,
                                    fcmToken = userFCMToken
                                ).let {
                                    viewModel.setUser(
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

