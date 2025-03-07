package com.superbeta.blibberly_home.report.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@Serializable
@Stable
data class ProfileReportDataModel(
    @SerialName("value") val value: String,
    @SerialName("createdAt") val createAt: String,
    @SerialName("status") val status: String,
    @SerialName("reporter") val reporter: String,
    @SerialName("reportedUser") val reportedUser: String
)