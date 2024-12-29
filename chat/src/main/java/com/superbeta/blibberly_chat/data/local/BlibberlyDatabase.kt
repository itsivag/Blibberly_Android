package com.superbeta.blibberly_chat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.blibberly.profile_ops.data.local.ProfileOpsDao
import com.blibberly.profile_ops.data.model.ProfileOpsConverters
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel
import com.superbeta.blibberly_chat.data.model.MessageConverters
import com.superbeta.blibberly_chat.data.model.MessageDataModel

@Database(entities = [MessageDataModel::class, ProfileOpsDataModel::class], version = 7)
@TypeConverters(MessageConverters::class, ProfileOpsConverters::class)
abstract class BlibberlyDatabase : RoomDatabase() {
    abstract fun MessagesDao(): MessagesDao
    abstract fun ProfileOpsDao(): ProfileOpsDao
}