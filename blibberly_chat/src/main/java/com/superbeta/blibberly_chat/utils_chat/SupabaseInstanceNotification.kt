package com.superbeta.blibberly_chat.utils_chat

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.gotrue.auth
import io.github.jan.supabase.postgrest.Postgrest

val supabase = createSupabaseClient(
    supabaseUrl = "https://dxyahfscoumjwjuwlgje.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR4eWFoZnNjb3VtandqdXdsZ2plIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTY5MTUzOTMsImV4cCI6MjAzMjQ5MTM5M30.DqthAS5M1CSeBFQf87TAxv57eMCalxxiPAbRp_XQ8AE"
) {
    install(Postgrest)
    install(Auth)
}
