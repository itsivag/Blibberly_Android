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

    @Query("SELECT * FROM profile_ops WHERE isMatched = 1")
    suspend fun getMatchedProfiles() : List<ProfileOpsDataModel>

    @Query("SELECT * FROM profile_ops WHERE isLiked = 1 and isMatched = 0")
    suspend fun getLikedProfiles() : List<ProfileOpsDataModel>
}