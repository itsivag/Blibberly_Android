package com.superbeta.blibberly.onBoarding.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.superbeta.blibberly.onBoarding.data.model.UserDataModel

@Dao
interface UserLocalDao {
    @Query("SELECT * FROM userInfo")
    suspend fun getUser(): UserDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUSer(userDataModel: UserDataModel)
}