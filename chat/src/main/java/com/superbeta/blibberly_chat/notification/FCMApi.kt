package com.superbeta.blibberly_chat.notification

import retrofit2.http.Body
import retrofit2.http.POST

interface FCMApi {

    @POST("/sendNotification")
    suspend fun sendNotification(
        @Body body: SendNotificationDto
    )

//    @POST("/broadcast")
//    suspend fun broadcast(
//        @Body body: SendNotificationDto
//    )
}