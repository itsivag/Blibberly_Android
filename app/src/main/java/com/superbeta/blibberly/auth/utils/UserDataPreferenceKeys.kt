package com.superbeta.blibberly.auth.utils

import androidx.datastore.preferences.core.stringPreferencesKey

class UserDataPreferenceKeys {
    companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }
}
