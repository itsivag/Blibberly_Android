package com.superbeta.blibberly_models

import android.os.Build
import androidx.annotation.Keep
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.Instant


@Keep
@Entity("userInfo")
@Immutable
@Serializable
@Stable
data class UserDataModel(
    @PrimaryKey @SerialName("email") @ColumnInfo("email") val email: String,
    @SerialName("name") @ColumnInfo("name") val name: String,
    @SerialName("dob") @ColumnInfo("dob") val dob: String,
    @SerialName("gender") @ColumnInfo("gender") val gender: String,
    @SerialName("aboutMe") @ColumnInfo("aboutMe") val aboutMe: String,
    @SerialName("interests") @ColumnInfo("interests") val interests: String,
    @SerialName("photoMetaData") @ColumnInfo("photoUri") val photoMetaData: PhotoMetaData,
    @SerialName("location") @ColumnInfo("location") val location: String,
    @SerialName("grind") @ColumnInfo("grind") val grind: String,
    @SerialName("languages") @ColumnInfo("languages") val languages: String,
    @SerialName("icebreaker") @ColumnInfo("icebreaker") val icebreaker: String,
    @SerialName("karma_point") @ColumnInfo("karma_point") val karmaPoint: Double,
    @SerialName("fcmToken") @ColumnInfo("fcmToken") val fcmToken: String,
    @Serializable(with = InstantSerializer::class)
    @SerialName("createdAt") @ColumnInfo("createdAt") val createdAt: Instant? = null,
    @Serializable(with = InstantSerializer::class)
    @SerialName("updatedAt") @ColumnInfo("updatedAt") val updatedAt: Instant? = null
)

@Serializable
@Stable
@Immutable
data class Grind(
    @SerialName("type") val type: String,
    @SerialName("name") val name: String
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


    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun fromInstant(value: Instant?): Long? {
        return value?.toEpochMilli() // Convert Instant to Long
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @TypeConverter
    fun toInstant(value: Long?): Instant? {
        return value?.let { Instant.ofEpochMilli(it) } // Convert Long to Instant
    }

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


object InstantSerializer : KSerializer<Instant> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Instant", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Instant) {
        encoder.encodeString(value.toString()) // Store as ISO-8601 string
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun deserialize(decoder: Decoder): Instant {
        return Instant.parse(decoder.decodeString()) // Convert back to Instant
    }
}
