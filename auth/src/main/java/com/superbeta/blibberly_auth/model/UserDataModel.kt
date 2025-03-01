package com.superbeta.blibberly_auth.model

import androidx.annotation.Keep
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Keep
@Entity("userInfo")
@Immutable
@Serializable
@Stable
data class UserDataModel(
    @PrimaryKey @SerialName("email") @ColumnInfo("email") val email: String,
    @SerialName("name") @ColumnInfo("name") val name: String,
    @SerialName("dob") @ColumnInfo("dob") val dob: Int,
    @SerialName("gender") @ColumnInfo("gender") val gender: String,
    @SerialName("height") @ColumnInfo("height") val height: Double,
//    @SerialName("weight") @ColumnInfo("weight") val weight: Double,
    @SerialName("aboutMe") @ColumnInfo("aboutMe") val aboutMe: String,
    @SerialName("interests") @ColumnInfo("interests") val interests: String,
    @SerialName("photoMetaData") @ColumnInfo("photoUri") val photoMetaData: PhotoMetaData,
    @SerialName("location") @ColumnInfo("location") val location: String,
    @SerialName("grind") @ColumnInfo("grind") val grind: Grind,
    @SerialName("languages") @ColumnInfo("languages") val languages: String,
    @SerialName("icebreaker") @ColumnInfo("icebreaker") val icebreaker: String,
    @SerialName("karma_point") @ColumnInfo("karma_point") val karmaPoint: Double,
    @SerialName("fcmToken") @ColumnInfo("fcmToken") val fcmToken: String
)

@Serializable
@Stable
@Immutable
data class Grind(
    val type: String,
    val name: String
)

@Serializable
@Stable
@Immutable
data class PhotoMetaData(
    @SerialName("blibmojiUrl") val blibmojiUrl: String,
    @SerialName("bgEmoji") val bgEmoji: String,
    @SerialName("bgColor") val bgColor: String

)


class UserDataModelConverters {

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()

    @TypeConverter
    fun photoMetaDataToJson(value: PhotoMetaData): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToPhotoMetaData(value: String): PhotoMetaData? =
        Gson().fromJson(value, PhotoMetaData::class.java)

    @TypeConverter
    fun grindToJson(value: Grind): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToGrind(value: String): Grind? =
        Gson().fromJson(value, Grind::class.java)
}