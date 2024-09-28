package com.superbeta.blibberly_chat.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

object MessageRoomInstanceProvider {
    fun getDb(context: Context): MessageDatabase {
        return Room.databaseBuilder(
            context,
            MessageDatabase::class.java, "blibberly_messages"
        ).fallbackToDestructiveMigration().build()
    }
}

//abstract class MessageRoomInstanceProvider : RoomDatabase() {
//    abstract fun getDao(): MessagesDao
//
//    companion object {
//        fun getDb(context: Context): MessageDatabase {
//            return Room.databaseBuilder(
//                context,
//                MessageDatabase::class.java, "blibberly_messages"
//            ).fallbackToDestructiveMigration().build()
//        }
//    }
//}