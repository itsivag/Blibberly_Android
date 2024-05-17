package com.superbeta.blibberly.onBoarding.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.superbeta.blibberly.onBoarding.data.model.UserDataModel

@Dao
interface UserLocalDao {
    @Query("SELECT * FROM userInfo")
    suspend fun getUser(): UserDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUSer(userDataModel: UserDataModel)

    @Query("UPDATE userInfo set name = :newName")
    suspend fun updateName(newName: String)

    @Query("UPDATE userInfo set age = :newAge")
    suspend fun updateAge(newAge: Int)

    @Query("UPDATE userInfo set height = :newHeight")
    suspend fun updateHeight(newHeight: Double)

    @Query("UPDATE userInfo set weight = :newWeight")
    suspend fun updateWeight(newWeight: Double)

    @Query("UPDATE userInfo set aboutMe = :newAboutMe")
    suspend fun updateAboutMe(newAboutMe: String)
}