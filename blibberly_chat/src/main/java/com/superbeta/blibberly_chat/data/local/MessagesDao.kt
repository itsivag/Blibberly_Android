package com.superbeta.blibberly_chat.data.local

import androidx.room.Dao
import androidx.room.Query
import com.superbeta.blibberly_chat.data.model.MessageDataModel

@Dao
interface MessagesDao {
    @Query("SELECT * FROM message")
    suspend fun getMessages(): List<MessageDataModel>
}