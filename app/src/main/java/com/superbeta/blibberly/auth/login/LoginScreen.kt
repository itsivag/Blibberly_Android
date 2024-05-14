package com.superbeta.blibberly.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(modifier: Modifier, navController: NavHostController) {


    var phoneNumber by remember {
        mutableStateOf(TextFieldValue())
    }

    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    isButtonEnabled = phoneNumber.text.length == 10

    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        val context = LocalContext.current
        val internalModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)


        Text(text = "Let's sign you in", style = MaterialTheme.typography.titleLarge)
        Text(text = "Welcome back.")
        Text(text = "You've been missed!")
        Spacer(modifier = Modifier.weight(1f))

        OutlinedTextField(
            singleLine = true,
            leadingIcon = {
                Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                    Text(text = "+91", modifier = Modifier.padding(horizontal = 4.dp))
                    Divider(
                        color = ColorDisabled, modifier = Modifier
                            .fillMaxHeight()
                            .width(1.dp)
                    )
                }
            },
            value = phoneNumber,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            onValueChange = {
                if (it.text.length <= 10)
                    phoneNumber = it
            },
            modifier = internalModifier,
            shape = RoundedCornerShape(14.dp),
            label = { Text(text = "10 digit mobile number") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ColorPrimary,
                unfocusedBorderColor = ColorDisabled,
            )
        )

        PrimaryButton(
            modifier = internalModifier,
            buttonText = "Get OTP",
            isButtonEnabled = isButtonEnabled,
            onClickMethod = { navController.navigate("otp_enter") })


        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.DarkGray)
            )
            Text(
                textAlign = TextAlign.Center,
                text = "or", color = Color.DarkGray, modifier = Modifier.padding(horizontal = 8.dp)
//                    .weight(0.5f)
            )
            Divider(
                modifier = Modifier
                    .weight(1f)
                    .background(color = Color.DarkGray)
            )
        }
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = internalModifier, onClick = { /*TODO*/ }, shape = RoundedCornerShape(14.dp)
        ) {
            Text(
                color = Color.Black,
                text = "Sign in with Google",
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}

