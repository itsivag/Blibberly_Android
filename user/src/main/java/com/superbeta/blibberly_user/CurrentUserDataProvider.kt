package com.superbeta.blibberly_user

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object CurrentUserDataProvider {
    private val auth = Firebase.auth

    fun getUserEmail(): String? = auth.currentUser?.email

}