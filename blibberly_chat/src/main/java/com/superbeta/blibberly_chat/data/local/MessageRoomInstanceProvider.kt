package com.superbeta.blibberly_chat.data.local

import android.content.Context
import androidx.room.Room

object MessageRoomInstanceProvider {
    fun getDb(context: Context): MessageDatabase {
        return Room.databaseBuilder(
            context,
            MessageDatabase::class.java, "blibberly_messages"
        ).fallbackToDestructiveMigration().build()
    }
}