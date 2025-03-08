package com.superbeta.blibberly.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.superbeta.blibberly.user.data.local.UserLocalDao
import com.superbeta.blibberly_models.UserDataModel

@Database(entities = [UserDataModel::class], version = 14)
@TypeConverters(com.superbeta.blibberly_models.UserDataModelConverters::class)
abstract class BlibberlyDatabase : RoomDatabase() {
    abstract fun userLocalDao(): UserLocalDao
}















