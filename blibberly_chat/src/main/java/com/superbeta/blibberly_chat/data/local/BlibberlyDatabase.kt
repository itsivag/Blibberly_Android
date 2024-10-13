package com.superbeta.blibberly_chat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blibberly.blibberly_likes.data.local.ProfileOpsDao
import com.superbeta.blibberly_chat.data.model.Converters
import com.superbeta.blibberly_chat.data.model.MessageDataModel

@Database(entities = [MessageDataModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class BlibberlyDatabase : RoomDatabase() {
    abstract fun MessagesDao(): MessagesDao
    abstract fun ProfileOpsDao(): ProfileOpsDao
}