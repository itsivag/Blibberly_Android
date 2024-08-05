package com.superbeta.blibberly_chat.notification

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun NotificationSampleScreen(
    viewModel: NotificationViewModel = viewModel()
) {
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = {
            scope.launch {
                val token = Firebase.messaging.token.await()
                Log.i("Notification Token", token)
            }
        }) {
            Text(text = "Get Token")
        }

        Button(onClick = {
            scope.launch {
                viewModel.sendMessage()
//                Log.i("Notification", )
            }
        }) {
            Text(text = "Send Message")
        }
    }
}