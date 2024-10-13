package com.blibberly.blibberly_likes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blibberly.blibberly_likes.data.model.ProfileOpsDataModel

@Dao
interface ProfileOpsDao {
    @Query("SELECT * FROM profile_ops WHERE userId = :userId")
    suspend fun getProfileOps(userId: String): ProfileOpsDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setProfileOps(profileOps: ProfileOpsDataModel)
}