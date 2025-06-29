package com.superbeta.blibberly_auth.presentation.ui
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Email
//import androidx.compose.material.icons.filled.Lock
////import androidx.compose.material3.HorizontalDivider
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Text
//import androidx.compose.material3.TextButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import com.superbeta.blibberly_auth.presentation.ui.components.GoogleSignInButton
//import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
//import com.superbeta.blibberly_auth.theme.ColorDisabled
//import com.superbeta.blibberly_auth.theme.ColorPrimary
//import com.superbeta.blibberly_auth.theme.components.PrimaryButton
//import kotlinx.coroutines.launch
//import org.koin.androidx.compose.koinViewModel
//
//@Composable
//fun SignInScreen(
//    modifier: Modifier,
////    navController: NavHostController,
//    authViewModel: AuthViewModel = koinViewModel(),
//    navigateToSignUp: () -> Unit,
////    navigateToHome: () -> Unit,
//) {
//
////    val authState by authViewModel.authState.collectAsStateWithLifecycle()
//
////    LaunchedEffect(key1 = authState) {
////        Log.i("Auth State", authState.toString())
////        when (authState) {
////            AuthState.SIGNED_IN -> {
////                navController.navigate(Screen.Home.route)
////            }
////            AuthState.SIGNED_OUT -> {}
////            AuthState.USER_EMAIL_STORED -> {}
////            AuthState.USER_EMAIL_STORAGE_ERROR -> {}
////            AuthState.USER_NOT_REGISTERED -> {
////                navController.navigate(Screen.OnBoarding.route)
////            }
////
////            AuthState.USER_REGISTERED -> {
////                navController.navigate(Screen.Home.route)
////            }
////
////            AuthState.ERROR -> {}
////            AuthState.LOADING -> {}
////            AuthState.IDLE -> {}
////        }
////    }
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
//    val context = LocalContext.current
//
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Top,
//        modifier = modifier
//            .fillMaxSize()
//            .padding(20.dp)
//    ) {
//        val internalModifier = Modifier
//            .fillMaxWidth()
//            .padding(vertical = 8.dp)
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
//            buttonText = "Sign In",
//            isButtonEnabled = isButtonEnabled,
//            onClickMethod = {
//                scope.launch {
////                    AuthRepositoryImpl().createUser(
////                        mEmail = email.text, mPassword = password.text
////                    )
//                authViewModel.signInWithEmail(email.text,password.text)
////                    val notificationRepo = NotificationRepoImpl()
////                    AuthRepositoryImpl().signInWithEmail(email.text, password.text)
//
//                }.invokeOnCompletion {
////                    navigateToHome()
//                }
////                navController.navigate("otp_enter")
//            })
//
//
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            HorizontalDivider(
//                modifier = Modifier.weight(1f)
//            )
//            Text(
//                text = "Or",
//                style = MaterialTheme.typography.labelSmall,
//                modifier = Modifier.padding(horizontal = 8.dp)
//            )
//            HorizontalDivider(
//                modifier = Modifier.weight(1f)
//            )
//        }
//
//        GoogleSignInButton()
//
//        Spacer(modifier = Modifier.weight(1f))
//
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(text = "Don't have an account?")
//            TextButton(onClick = { navigateToSignUp() }) {
//                Text(text = "Sign Up", color = Color.Blue)
//            }
//        }
//    }
//}
//
