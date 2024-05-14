package com.superbeta.blibberly.onBoarding.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.theme.ColorDisabled
import com.superbeta.blibberly.ui.theme.ColorPrimary
import com.superbeta.blibberly.ui.theme.components.PrimaryButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutMeScreen(modifier: Modifier, navController: NavHostController) {
    var isButtonEnabled by remember {
        mutableStateOf(false)
    }

    var aboutMe by remember {
        mutableStateOf(TextFieldValue())
    }

    isButtonEnabled = true

    Column(modifier = modifier) {

        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.arrow_back),
                        contentDescription = "back"
                    )
                }
            })

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Add an 'About Me' to let others get to know you better. Your story matters on Blibberly!",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        )

        Text(
            text = "\nIntroduce yourself! \uD83C\uDF1F",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            minLines = 3,
            value = aboutMe,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            onValueChange = { value -> aboutMe = value },
            shape = RoundedCornerShape(14.dp),
            placeholder = {
                Text(
                    text = "Yo Blibbers! \uD83C\uDF1F I'm Mia, a coffee aficionado ☕ and aspiring travel blogger \uD83C\uDF0D. Catch me vibing to indie tunes or geeking out over the latest tech trends. Let's swap memes, share playlists, and see if our stars align on Blibberly! Let's make some memories, fam! \uD83D\uDE80✨\"",
                    color = ColorDisabled,
//                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = ColorPrimary,
                unfocusedBorderColor = ColorDisabled,
            )
        )

        PrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), buttonText = "Next", isButtonEnabled = isButtonEnabled
        ) {
            navController.navigate("skill_and_interests")
        }
    }

}