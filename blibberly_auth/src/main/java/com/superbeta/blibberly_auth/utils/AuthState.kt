package com.superbeta.blibberly_auth.utils

enum class AuthState {
    SIGNED_IN,
    SIGNED_OUT,
    USER_EMAIL_STORED,
    USER_EMAIL_STORAGE_ERROR,
    USER_NOT_REGISTERED,
    ERROR,
    LOADING,
    IDLE,
    USER_REGISTERED,
}