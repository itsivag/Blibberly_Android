package com.blibberly.profile_ops.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Keep
@Entity("profile_ops")
data class ProfileOpsDataModel(
    @PrimaryKey @ColumnInfo("userId") val userId: String,
    @ColumnInfo("userEmail") val userEmail: String,
    @ColumnInfo("isLiked") val isLiked: Boolean,
    @ColumnInfo("isDisliked") val isDisliked: Boolean,
    @ColumnInfo("isMatched") val isMatched: Boolean,
    @ColumnInfo("isReported") val isReported: Boolean,
    @ColumnInfo("firstChatTimestamp") val firstChatTimestamp: String? = null,
    @ColumnInfo("likedTimestamp") val likedTimestamp: String? = null,
    @ColumnInfo("matchedTimestamp") val matchedTimestamp: String? = null,
    @ColumnInfo("dislikedTimestamp") val dislikedTimestamp: String? = null,
    @ColumnInfo("reportedTimestamp") val reportedTimestamp: String? = null,
)
