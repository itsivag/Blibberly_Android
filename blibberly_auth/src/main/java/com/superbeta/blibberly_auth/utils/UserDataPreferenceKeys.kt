package com.superbeta.blibberly_auth.utils

import androidx.datastore.preferences.core.stringPreferencesKey

class UserDataPreferenceKeys {
    companion object {
        val USER_ID = stringPreferencesKey("user_id")
        val USER_EMAIL = stringPreferencesKey("user_email")
    }
}
