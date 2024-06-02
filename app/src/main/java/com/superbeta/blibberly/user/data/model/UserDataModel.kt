package com.superbeta.blibberly.user.data.model

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
    @PrimaryKey @SerialName("phoneNumber") @ColumnInfo("phoneNumber") val phoneNum: String,

    @SerialName("name") @ColumnInfo("name") val name: String,

    @SerialName("age") @ColumnInfo("age") val age: Int,

    @SerialName("gender") @ColumnInfo("gender") val gender: String,

    @SerialName("height") @ColumnInfo("height") val height: Double,

    @SerialName("weight") @ColumnInfo("weight") val weight: Double,

    @SerialName("aboutMe") @ColumnInfo("aboutMe") val aboutMe: String,

    @SerialName("interests") @ColumnInfo("interests") val interests: List<String>,

    @SerialName("photoUri") @ColumnInfo("photoUri") val photoUri: String
)


class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}