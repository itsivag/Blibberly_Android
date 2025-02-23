package com.superbeta.blibberly_auth.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.superbeta.blibberly_auth.utils.UserDataPreferenceKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDataStoreServiceImpl(private val userPreferencesDataStore: DataStore<Preferences>) :
    AuthDataStoreService {
//    override suspend fun setUserData(user: UserInfo) {
//        try {
//            Log.i("Storing User Data In Data Store", user.toString())
//            val userInfoJson = Gson().toJson(user)
//            userPreferencesDataStore.edit { preferences ->
//                preferences[UserDataPreferenceKeys.USER_INFO] = userInfoJson
//            }
//        } catch (e: Exception) {
//
//        }
//    }

//    override suspend fun setUserData(user: UserProfile) {
//        try {
//            Log.i("Storing User Data In Data Store", user.toString())
//            val userInfoJson = Gson().toJson(user)
//            userPreferencesDataStore.edit { preferences ->
//                preferences[UserDataPreferenceKeys.USER_INFO] = userInfoJson
//            }
//        } catch (e: Exception) {
//            Log.e("AuthDataStoreServiceImpl", "Error setting data preference : $e")
//        }
//    }
//
//    override suspend fun getUserData(): Flow<UserProfile?> {
//        return userPreferencesDataStore.data.map { prefs ->
//            Gson().fromJson(prefs[UserDataPreferenceKeys.USER_INFO], UserProfile::class.java)
//        }
//    }
//
//    override suspend fun getUserData(): Flow<UserInfo?> {
//        return userPreferencesDataStore.data.map { preferences ->
//            preferences[UserDataPreferenceKeys.USER_EMAIL]
//        }
//    }

//    override suspend fun deleteUserData() {
//        userPreferencesDataStore.edit { preferences ->
//            preferences.clear()
//        }

//    }

    override suspend fun setAccessToken(token: String) {
        try {
            Log.i("Storing Access Token In Data Store", token)
            userPreferencesDataStore.edit { preferences ->
                preferences[UserDataPreferenceKeys.ACCESS_TOKEN] = token
            }
        } catch (e: Exception) {
            Log.e("AuthDataStoreServiceImpl", "Error setting access token into data pref : $e")
        }
    }

    override suspend fun getAccessToken(): Flow<String?> {
        return userPreferencesDataStore.data.map {
            it[UserDataPreferenceKeys.ACCESS_TOKEN]
        }
    }

    override suspend fun deleteAccessToken() {
        userPreferencesDataStore.edit {
            it.clear()
        }
    }
}