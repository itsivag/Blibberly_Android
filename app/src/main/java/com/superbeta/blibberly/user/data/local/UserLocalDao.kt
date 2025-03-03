package com.superbeta.blibberly.user.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.superbeta.blibberly_auth.model.PhotoMetaData
import com.superbeta.blibberly_auth.model.UserDataModel

@Dao
interface UserLocalDao {
    @Query("SELECT * FROM userInfo")
    suspend fun getUser(): UserDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setUser(userDataModel: UserDataModel)

    @Query("UPDATE userInfo set name = :newName")
    suspend fun updateName(newName: String)

    @Query("UPDATE userInfo set dob = :newDob")
    suspend fun updateAge(newDob: String)

//    @Query("UPDATE userInfo set height = :newHeight")
//    suspend fun updateHeight(newHeight: Double)

//    @Query("UPDATE userInfo set weight = :newWeight")
//    suspend fun updateWeight(newWeight: Double)

    @Query("UPDATE userInfo set aboutMe = :newAboutMe")
    suspend fun updateAboutMe(newAboutMe: String)

    @Query("UPDATE userInfo set interests = :newInterests")
    suspend fun updateInterests(newInterests: List<String>)

    @Query("UPDATE userInfo set photoUri = :photoMetaData")
    suspend fun updatePhotoMetaData(photoMetaData: PhotoMetaData)

    @Query("DELETE from userInfo")
    suspend fun deleteUserInfo()


}