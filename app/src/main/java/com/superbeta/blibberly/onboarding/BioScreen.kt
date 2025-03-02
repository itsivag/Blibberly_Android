package com.superbeta.blibberly.onboarding

import android.util.Log
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.gson.Gson
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorDisabled
import com.superbeta.blibberly.ui.TextColorGrey
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly_auth.model.Grind
import com.superbeta.blibberly_auth.model.PhotoMetaData
import com.superbeta.blibberly_auth.model.UserDataModel
import com.superbeta.blibberly_home.utils.FontProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

//    var weight by remember {
//        mutableStateOf(TextFieldValue())
//    }

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

    var location by remember {
        mutableStateOf("")
    }

    var language by remember {
        mutableStateOf("")
    }

    var userFCMToken by remember {
        mutableStateOf("")
    }

    var workingAt by remember {
        mutableStateOf(TextFieldValue())
    }

    var studyOrWork by remember {
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
                age = TextFieldValue(userData.dob.toString())
                height = TextFieldValue(userData.height.toString())
//                weight = TextFieldValue(userData.weight.toString())
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

    var isButtonEnabled by remember(name.text, age.text, height.text, gender) {
        mutableStateOf(
            name.text.isNotEmpty() && age.text.isNotEmpty() && height.text.isNotEmpty() && gender.isNotEmpty()
        )
    }

    LaunchedEffect(name.text, age.text, height.text, gender) {
        isButtonEnabled =
            (name.text.isNotEmpty() && age.text.isNotEmpty() && height.text.isNotEmpty() && gender.isNotEmpty())
//                weight.text.isNotEmpty())
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

//        item {
//            Text(
//                text = "Sharpen your profile! Add your 'Vitals' to stand out from the crowd and make a lasting impression!",
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier
//                    .padding(16.dp)
//                    .background(
//                        color = MaterialTheme.colorScheme.secondary,
//                        shape = RoundedCornerShape(12.dp)
//                    )
//                    .padding(16.dp)
//            )
//
//        }
//        item {
//            Text(
//                text = "\nYour Vitals\uD83C\uDF1F",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(16.dp)
//            )
//        }
//
//        item {
//            TextFieldWithLabel(
//                textFieldValue = name,
//                onTextFieldValueChange = { value -> name = value },
//                "Name",
//                "Mega",
//                keyboardOptions = KeyboardOptions(),
//                isError = name.text.isNotEmpty() && name.text.length < 4,
//                errorText = "name should be at least 4 characters"
//            )
//        }
//
//        item {
//            TextFieldWithLabel(
//                textFieldValue = age,
//                onTextFieldValueChange = { value -> age = value },
//                "Age",
//                "22",
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                isError = age.text.isNotEmpty() && (age.text.toInt() < 18 || age.text.toInt() > 110),
//                errorText = "you should be at least 18 years old"
//            )
//        }
//
//        item {
//            Text(
//                text = "Gender",
//                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
//                style = MaterialTheme.typography.bodyMedium,
//            )
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp, horizontal = 8.dp)
//            ) {
//                IconButton(modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 8.dp)
//                    .border(
//                        width = if (gender == "F") 2.dp else 1.dp,
//                        color = if (gender == "F") ColorPrimary else ColorDisabled,
//                        shape = RoundedCornerShape(20.dp)
//                    )
//                    .padding(16.dp), onClick = { gender = "F" }) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(id = R.drawable.female),
//                        contentDescription = "Female"
//                    )
//                }
//
//                IconButton(modifier = Modifier
//                    .weight(1f)
//                    .padding(horizontal = 8.dp)
//                    .border(
//                        width = if (gender == "M") 2.dp else 1.dp,
//                        color = if (gender == "M") ColorPrimary else ColorDisabled,
//                        shape = RoundedCornerShape(20.dp)
//                    )
//                    .padding(16.dp), onClick = { gender = "M" }) {
//                    Icon(
//                        imageVector = ImageVector.vectorResource(id = R.drawable.male),
//                        contentDescription = "Male"
//                    )
//                }
//            }
//        }
//        item {
////            Row(modifier = Modifier.padding(vertical = 8.dp)) {
//            Column(modifier = Modifier.padding(top = 16.dp)) {
//                Text(
//                    text = "Height",
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = Modifier.padding(horizontal = 16.dp)
//                )
//
//                OutlinedTextField(
//                    isError = height.text.isNotEmpty() && (height.text.toDouble() < 0),
//                    supportingText = {
//                        if (height.text.isNotEmpty() && (height.text.toDouble() < 0)) Text(
//                            text = "Enter a logical height", color = Color.Red
//                        ) else Text(text = "")
//                    },
//                    maxLines = 1,
//                    value = height,
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .fillMaxWidth(),
//                    onValueChange = { value -> height = value },
//                    shape = RoundedCornerShape(14.dp),
//                    placeholder = {
//                        Text(
//                            text = "175",
//                            color = ColorDisabled,
//                        )
//                    },
//                    colors = OutlinedTextFieldDefaults.colors(
//                        focusedBorderColor = ColorPrimary,
//                        unfocusedBorderColor = ColorDisabled,
//                    ),
//                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                    trailingIcon = {
//                        Text(
//                            text = "cm",
//                            color = ColorDisabled,
//                            style = MaterialTheme.typography.labelLarge
//                        )
//                    },
//
//                    )
//            }
//        }

        item {
//            I’m [Name], born on [DOB], and I’m [Gender]. I live in [Location] and speak [Languages Known]. I [Work/Study] at [Company/University] as a [Job Title/Major].
            Text(
                text = "Let’s Get to Know You!",
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
                    text = "I’m",
                    color = TextColorGrey,
                    style = MaterialTheme.typography.titleLarge,
                )

                FillInTheBlanksTextField(hintText = "Siva", textFieldValue = name.text) {
                    name = TextFieldValue(it)
                }


            }
        }

        item {
            Text(
                text = "born on ",
                color = TextColorGrey,
                style = MaterialTheme.typography.titleLarge,
            )
            DatePickerFieldToModal()
//                StudyOrWorkDropDown(studyOrWork,
//                    editStudyOrWork = { value: String -> studyOrWork = value })
            Text(
                text = "at",
                color = TextColorGrey,
                style = MaterialTheme.typography.titleLarge,
            )
        }

        item {
            Text(
                text = "and I’m",
                color = TextColorGrey,
                style = MaterialTheme.typography.titleLarge,
            )

            FillInTheBlanksDropDown(
                listOf("Male", "Female"), gender,
                onValueChange = { value: String -> gender = value })
        }

        item {
            Text(
                text = "I live in",
                color = TextColorGrey,
                style = MaterialTheme.typography.titleLarge,
            )

            FillInTheBlanksTextField(hintText = "Chennai, Tamil Nadu", textFieldValue = location) {
                location = it
            }
        }

        item {
            Text(
                text = " and speak ",
                color = TextColorGrey,
                style = MaterialTheme.typography.titleLarge,
            )

            FillInTheBlanksTextField(hintText = "Tamil", textFieldValue = language) {
                language = it
            }
        }

        item {
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
                FillInTheBlanksDropDown(
                    listOf(
                        "Work", "Own", "Study", "Freelance", "Intern", "Hustle", "Create"
                    ),
                    studyOrWork,
                    onValueChange = { value: String -> studyOrWork = value })
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
                            userViewModel.getUserEmail()?.let { email ->
                                UserDataModel(
                                    email = email,
                                    name = name.text,
                                    dob = age.text.toInt(),
                                    height = height.text.toDoubleOrNull() ?: 0.0,
                                    aboutMe = aboutMe,
                                    interests = interests,
                                    gender = gender,
                                    photoMetaData = PhotoMetaData("", "", ""),
                                    location = "",
                                    grind = Grind("", ""),
                                    languages = "",
                                    icebreaker = "",
                                    karmaPoint = 0.0,
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

@Composable
fun FillInTheBlanksTextField(
    hintText: String,
    textFieldValue: String, changeTextFieldValue: (String) -> Unit
) {
    TextField(
        value = textFieldValue,
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
                text = hintText,
                color = ColorDisabled,
                textDecoration = TextDecoration.Underline
            )
        },
        onValueChange = { changeTextFieldValue(it) },
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
        ),
    )
}

@Composable
fun DatePickerFieldToModal(modifier: Modifier = Modifier) {
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }

    OutlinedTextField(
        textStyle = TextStyle(
            textDecoration = TextDecoration.Underline,
            fontFamily = FontProvider.poppinsFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            color = TextColorGrey
        ),
        value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        onValueChange = { },
        placeholder = {
            Text(
                style = MaterialTheme.typography.titleLarge,
                text = "MM/DD/YYYY",
                color = ColorDisabled,
                textDecoration = TextDecoration.Underline
            )
        },
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
        ),
        modifier = modifier
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}