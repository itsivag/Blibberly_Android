package com.superbeta.blibberly_auth.presentation.ui
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
//import com.superbeta.blibberly_auth.theme.components.PrimaryButton
//import kotlinx.coroutines.launch
//import org.koin.androidx.compose.koinViewModel
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SignUpScreen(
//    modifier: Modifier,
//    navigateBack: () -> Unit,
//    authViewModel: AuthViewModel = koinViewModel()
//) {
//
//
//    var email by remember {
//        mutableStateOf(TextFieldValue())
//    }
//
//    var password by remember {
//        mutableStateOf(TextFieldValue())
//    }
//
//    var isButtonEnabled by remember {
//        mutableStateOf(true)
//    }
//
////    isButtonEnabled = phoneNumber.text.length == 10
//
//    val scope = rememberCoroutineScope()
//
//    val context = LocalContext.current
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top,
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(20.dp)
//    ) {
//        val internalModifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
//
//        TopAppBar(title = {}, navigationIcon = {
//            IconButton(onClick = { navigateBack() }) {
////                Icon(
////                    imageVector = ImageVector.vectorResource(id = R.drawable.arrow_back),
////                    contentDescription = "Go back"
////                )
//            }
//        })
//
//        Text(text = "Let's sign you in", style = MaterialTheme.typography.titleLarge)
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        OutlinedTextField(
//            singleLine = true,
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Email, contentDescription = "Email"
//                )
//            },
//            value = email,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
//            onValueChange = {
//                email = it
//            },
//            modifier = internalModifier,
//            shape = RoundedCornerShape(14.dp),
//            label = { Text(text = "Email") },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = ColorPrimary,
//                unfocusedBorderColor = ColorDisabled,
//            )
//        )
//
//
//        OutlinedTextField(
//            singleLine = true,
//            leadingIcon = {
//                Icon(
//                    imageVector = Icons.Default.Lock, contentDescription = "Password"
//                )
//            },
//            value = password,
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            onValueChange = {
//                password = it
//            },
//            modifier = internalModifier,
//            shape = RoundedCornerShape(14.dp),
//            label = { Text(text = "Password") },
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedBorderColor = ColorPrimary,
//                unfocusedBorderColor = ColorDisabled,
//            )
//        )
//
//
//        PrimaryButton(modifier = internalModifier,
//            buttonText = "Sign Up",
//            isButtonEnabled = isButtonEnabled,
//            onClickMethod = {
//                scope.launch {
//                    authViewModel.signUpWithEmail(email.text, password.text)
//                }
//            })
//
//    }
//}