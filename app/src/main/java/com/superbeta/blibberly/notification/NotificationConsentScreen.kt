package com.superbeta.blibberly.notification

import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superbeta.blibberly.R
import com.superbeta.blibberly.ui.ColorPrimary
import com.superbeta.blibberly.ui.TextColorGrey
import com.superbeta.blibberly.ui.WhiteWithAlpha
import com.superbeta.blibberly.ui.components.PrimaryButton
import com.superbeta.blibberly.utils.FontProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NotificationConsentScreen(
    modifier: Modifier,
    notificationViewModel: NotificationViewModel = koinViewModel(),
    navigateToQueueScreen: () -> Unit
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    //check state of notification permission
    val requestPermissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                navigateToQueueScreen()
            } else {
                navigateToQueueScreen()
                //open settings
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    val settingsIntent: Intent =
//                        Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
//                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            .putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
//
//                    context.startActivity(settingsIntent)
//            }

            }
        }

    val notificationConsentFeatureList =
        listOf("Real-time chat alerts", "Safety & account updates", "Stay connected effortlessly")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = ColorPrimary)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Text(
            text = "Allow notifications\n" +
                    " to keep the convo flowing!",
            style = TextStyle(
                fontSize = 28.sp,
                color = Color.White,
                fontFamily = FontProvider.poppinsFontFamily,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )
        )
        Image(
            painter = painterResource(id = R.drawable.notification_bell),
            contentDescription = "Notification Bell",
            modifier = Modifier.fillMaxWidth()
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            repeat(notificationConsentFeatureList.size) {
                Text(
                    text = notificationConsentFeatureList[it],
                    modifier = Modifier
                        .padding(8.dp)
                        .background(
                            color = WhiteWithAlpha,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(vertical = 12.dp, horizontal = 16.dp),
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = FontProvider.poppinsFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            PrimaryButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                buttonContainerColor = WhiteWithAlpha,
                buttonText = "May be later",
                isButtonEnabled = true, textColor = Color.White,
                onClickMethod = {
                    navigateToQueueScreen()
                })

            PrimaryButton(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
                buttonText = "Allow",
                buttonContainerColor = Color.White,
                isButtonEnabled = true,
                textColor = TextColorGrey,
                hapticsEnabled = true,
                onClickMethod = {
                    scope.launch {
                        notificationViewModel.requestPermission(requestPermissionLauncher)
                    }
                })
        }
    }
}