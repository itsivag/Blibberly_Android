package com.superbeta.blibberly.utils

import android.content.Context
import androidx.room.Room

object RoomInstanceProvider {
    fun getDb(context: Context): BlibberlyDatabase {
        return Room.databaseBuilder(
            context,
            BlibberlyDatabase::class.java, "blibberly"
        ).fallbackToDestructiveMigration().build()
    }
}