package com.blibberly.profile_ops.data.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable

@Keep
@Entity("profile_ops")
@Immutable
@Serializable
@Stable
data class ProfileOpsDataModel(
    @PrimaryKey @ColumnInfo("userEmail") val userEmail: String,
    @ColumnInfo("likedProfiles") val likedProfiles: List<ProfileOp>,
    @ColumnInfo("dislikedProfiles") val dislikedProfiles: List<ProfileOp>,
    @ColumnInfo("matchedProfiles") val matchedProfiles: List<ProfileOp>,
    @ColumnInfo("unMatchedProfiles") val unMatchedProfiles: List<ProfileOp>,
    @ColumnInfo("reportedProfiles") val reportedProfiles: List<ProfileOp>,
)

@Keep
@Immutable
@Serializable
@Stable
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
