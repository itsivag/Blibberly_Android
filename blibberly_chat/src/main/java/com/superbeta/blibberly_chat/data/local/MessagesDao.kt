package com.superbeta.blibberly_chat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.superbeta.blibberly_chat.data.model.MessageDataModel

@Dao
interface MessagesDao {
//    @Query("SELECT * FROM message WHERE senderID = :userEmail OR receiverID = :userId")
    @Query("SELECT * FROM message")
//    suspend fun getMessages(userEmail: String, userId: String?): List<MessageDataModel>
    suspend fun getMessages(): List<MessageDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMessages(messages: List<MessageDataModel>)
}