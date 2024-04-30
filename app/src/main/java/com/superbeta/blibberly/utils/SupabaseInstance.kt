package com.superbeta.blibberly.utils

import io.github.jan.supabase.compose.auth.ComposeAuth
import io.github.jan.supabase.compose.auth.googleNativeLogin
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest

object SupabaseInstance {
    val supabase = createSupabaseClient(
        supabaseUrl = "https://pzquvvymzamqjfblibbp.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InB6cXV2dnltemFtcWpmYmxpYmJwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTM1ODcyMzIsImV4cCI6MjAyOTE2MzIzMn0.-IM4z9SoX68Es-EmRGMAsuxoRGKnAM9ESvSlmeqED3Q"
    ) {
        install(Auth)
        install(Postgrest)
        install(ComposeAuth) {
            googleNativeLogin(serverClientId = "google-client-id")
        }
    }
}