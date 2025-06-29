package com.superbeta.blibberly.notification

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

class NotificationViewModel(private val notificationUtil: NotificationUtil) : ViewModel() {

    fun requestPermission(requestPermissionLauncher: ManagedActivityResultLauncher<String, Boolean>) {
        notificationUtil.askNotificationPermission(requestPermissionLauncher)
    }

}
