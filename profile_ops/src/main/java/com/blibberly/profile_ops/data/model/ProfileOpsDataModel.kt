package com.blibberly.profile_ops.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

//@Keep
//@Entity("profile_ops")
//data class ProfileOpsDataModel(
//    @PrimaryKey @ColumnInfo("userEmail") val userEmail: String,
//    @ColumnInfo("isLiked") val isLiked: Boolean,
//    @ColumnInfo("isDisliked") val isDisliked: Boolean,
//    @ColumnInfo("isMatched") val isMatched: Boolean,
//    @ColumnInfo("isReported") val isReported: Boolean,
//    @ColumnInfo("firstChatTimestamp") val firstChatTimestamp: String? = null,
//    @ColumnInfo("likedTimestamp") val likedTimestamp: String? = null,
//    @ColumnInfo("matchedTimestamp") val matchedTimestamp: String? = null,
//    @ColumnInfo("dislikedTimestamp") val dislikedTimestamp: String? = null,
//    @ColumnInfo("reportedTimestamp") val reportedTimestamp: String? = null,
//)
//

@Keep
@Entity("profile_ops")
data class ProfileOpsDataModel(
    @PrimaryKey @ColumnInfo("userEmail") val userEmail: String,
    @ColumnInfo("likedProfiles") val likedProfiles: List<ProfileOp>,
    @ColumnInfo("dislikedProfiles") val dislikedProfiles: List<ProfileOp>,
    @ColumnInfo("matchedProfiles") val matchedProfiles: List<ProfileOp>,
    @ColumnInfo("unmatchedProfiles") val unMatchedProfiles: List<ProfileOp>,
    @ColumnInfo("reportedProfiles") val reportedProfiles: List<ProfileOp>,
)

data class ProfileOp(
    val userEmail: String,
    val timeStamp: String
)


class ProfileOpsConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromProfileOpList(value: List<ProfileOp>?): String {
        return gson.toJson(value)
    }

    @TypeConverter
    fun toProfileOpList(value: String): List<ProfileOp> {
        val listType = object : TypeToken<List<ProfileOp>>() {}.type
        return gson.fromJson(value, listType)
    }
}
