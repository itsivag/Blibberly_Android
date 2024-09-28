package com.superbeta.blibberly_chat.data.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.errorprone.annotations.Immutable
import com.google.gson.Gson

@Keep
@Entity("message")
@Immutable
//@Stable

data class MessageDataModel(
    @PrimaryKey @ColumnInfo("messageId") val messageId: String,
    @ColumnInfo("content") val content: String,
    @ColumnInfo("senderId") val senderID: String,
    @ColumnInfo("receiverId") val receiverID: String,
    @ColumnInfo("timeStamp") val timeStamp: String,
    @ColumnInfo("isDelivered") val isDelivered: Boolean,
    @ColumnInfo("isRead") val isRead: Boolean
)

class Converters {

    @TypeConverter
    fun listToJson(value: List<String>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<String>::class.java).toList()
}