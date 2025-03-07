package com.superbeta.blibberly_home.presentation.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly_auth.theme.ColorPrimary
import com.superbeta.blibberly_auth.utils.FontProvider
import com.superbeta.blibberly_home.R
import com.superbeta.blibberly_utils.ageFromDateCalculator
import com.superbeta.blibberly_utils.capitalized

@Composable
fun BioCard(user: com.superbeta.blibberly_models.UserDataModel) {
//    val sampleBio = BioDataModel(
//        gender = "male",
//        age = 21,
//        location = "Chennai, India"
//    )

    val scrollState = rememberScrollState()

    Card(
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray
        ),
        modifier = Modifier
            .fillMaxWidth()
//            .scrollable(scrollState, orientation = Orientation.Horizontal)
//            .padding(16.dp),
    ) {
        BlibberlyCardTitle(title = "Bio", emoji = "ℹ\uFE0F")

        Row(
            modifier = Modifier
                .padding(bottom = 8.dp, start = 12.dp, end = 12.dp, top = 8.dp)
                .scrollable(scrollState, orientation = Orientation.Horizontal)
                .fillMaxWidth()
        ) {
            BioCardChip(user.gender.capitalized(), 1)
            BioCardChip(ageFromDateCalculator(user.dob).toString(), 2)
            BioCardChip(user.location.capitalized(), 3)
        }
    }
}


@Composable
fun BioCardChip(value: String, type: Int) {
    val iconType = when (type) {
        1 -> {
            if (value.lowercase() == "male")
                R.drawable.male
            else
                R.drawable.female
        }

        2 -> {
            R.drawable.age
        }

        3 -> {
            R.drawable.location
        }

        else -> {
            R.drawable.chat
        }
    }
    Row(
        modifier = Modifier
            .padding(4.dp)
            .border(
                border = BorderStroke(width = 1.dp, color = ColorPrimary),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconType),
            contentDescription = "Gender",
            modifier = Modifier.padding(start = 4.dp)
        )

        Text(
            text = value,
            fontFamily = FontProvider.dmSansFontFamily,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 4.dp),
            fontWeight = FontWeight.SemiBold
        )
    }
}
