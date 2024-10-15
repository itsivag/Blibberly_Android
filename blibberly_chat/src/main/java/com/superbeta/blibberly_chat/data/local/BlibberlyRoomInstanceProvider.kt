package com.superbeta.blibberly_chat.data.local

import android.content.Context
import androidx.room.Room

object BlibberlyRoomInstanceProvider {
    fun getDatabase(context: Context): BlibberlyDatabase {
        return Room.databaseBuilder(
            context,
            BlibberlyDatabase::class.java, "blibberly_db"
        ).fallbackToDestructiveMigration().build()
    }
}

