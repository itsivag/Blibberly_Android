package com.superbeta.blibberly_chat.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.errorprone.annotations.Immutable
import com.google.gson.Gson
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Entity("message")
@Immutable
@Serializable
data class MessageDataModel(
    @SerialName("messageId") @PrimaryKey @ColumnInfo("messageId") val messageId: String,
    @SerialName("content") @ColumnInfo("content") val content: String,
    @SerialName("senderEmail") @ColumnInfo("senderEmail") val senderEmail: String,
    @SerialName("receiverEmail") @ColumnInfo("receiverEmail") val receiverEmail: String,
    @SerialName("timeStamp") @ColumnInfo("timeStamp") val timeStamp: String,
    @SerialName("isDelivered") @ColumnInfo("isDelivered") val isDelivered: Boolean,
    @SerialName("isRead") @ColumnInfo("isRead") val isRead: Boolean
)

class MessageConverters {

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}