package com.superbeta.profile_ops.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.blibberly.profile_ops.data.model.ProfileOpsDataModel

@Dao
interface ProfileOpsDao {
    @Query("SELECT * FROM profile_ops WHERE userEmail = :userEmail")
    suspend fun getProfileOps(userEmail: String): ProfileOpsDataModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setProfileOps(profileOps: ProfileOpsDataModel)

//    @Query("SELECT * FROM profile_ops WHERE isMatched = 1")
//    suspend fun getMatchedProfiles() : List<ProfileOpsDataModel>

//    @Query("SELECT * FROM profile_ops WHERE isLiked = 1 and isMatched = 0")
//    suspend fun getLikedProfiles() : List<ProfileOpsDataModel>
}