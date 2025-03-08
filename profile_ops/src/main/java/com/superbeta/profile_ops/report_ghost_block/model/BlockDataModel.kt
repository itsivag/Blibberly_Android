package com.superbeta.profile_ops.report_ghost_block.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Immutable
@Serializable
@Stable
data class BlockDataModel(
    @SerialName("blocker") val blocker: String,
    @SerialName("blockedUser") val blockedUser: String,
//    @SerialName("createdAt") val createAt: String,
)
