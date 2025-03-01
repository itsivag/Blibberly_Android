package com.superbeta.blibberly.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.superbeta.blibberly.user.data.local.UserLocalDao
import com.superbeta.blibberly_auth.model.UserDataModel
import com.superbeta.blibberly_auth.model.UserDataModelConverters

@Database(entities = [UserDataModel::class], version = 7)
@TypeConverters(UserDataModelConverters::class)
abstract class BlibberlyDatabase : RoomDatabase() {
    abstract fun userLocalDao(): UserLocalDao
}