package com.superbeta.blibberly_chat.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.superbeta.blibberly_chat.data.model.MessageDataModel

@Dao
interface MessagesDao {
    @Query("SELECT * FROM message WHERE (senderEmail = :currUserEmail AND receiverEmail = :receiverEmail) OR (senderEmail = :receiverEmail AND receiverEmail = :currUserEmail)")
    suspend fun getMessages(currUserEmail: String, receiverEmail: String): List<MessageDataModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMessages(messages: List<MessageDataModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSingleMessage(message: MessageDataModel)
}