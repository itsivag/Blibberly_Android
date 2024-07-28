package com.superbeta.blibberly.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.superbeta.blibberly.user.data.local.UserLocalDao
import com.superbeta.blibberly.user.data.model.Converters
import com.superbeta.blibberly.user.data.model.UserDataModel

@Database(entities = [UserDataModel::class], version = 5)
@TypeConverters(Converters::class)
abstract class BlibberlyDatabase : RoomDatabase() {
    abstract fun userLocalDao(): UserLocalDao
}