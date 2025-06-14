package com.superbeta.blibberly_auth.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(name = "user")