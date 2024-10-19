package com.superbeta.blibberly_auth.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.superbeta.blibberly_auth.utils.UserDataPreferenceKeys
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthDataStoreServiceImpl(private val userPreferencesDataStore: DataStore<Preferences>) :
    AuthDataStoreService {
    override suspend fun setUserData(user: UserInfo) {
        Log.i("Storing User Data In Data Store", user.toString())
        userPreferencesDataStore.edit { preferences ->
            preferences[UserDataPreferenceKeys.USER_ID] = user.id
            preferences[UserDataPreferenceKeys.USER_EMAIL] = user.email ?: ""
        }
    }

    override suspend fun getUserData(): Flow<String?> {
        return userPreferencesDataStore.data.map { preferences ->
            preferences[UserDataPreferenceKeys.USER_EMAIL]
        }
    }
}