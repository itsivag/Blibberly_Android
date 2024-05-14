package com.superbeta.blibberly.utils

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.superbeta.blibberly.onBoarding.data.UserLocalDao
import com.superbeta.blibberly.onBoarding.data.model.Converters
import com.superbeta.blibberly.onBoarding.data.model.UserDataModel

@Database(entities = [UserDataModel::class], version = 1)
@TypeConverters(Converters::class)
abstract class BlibberlyDatabase : RoomDatabase() {
    abstract fun userLocalDao(): UserLocalDao
}