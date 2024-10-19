package com.superbeta.blibberly_auth.di

import androidx.credentials.CredentialManager
import com.superbeta.blibberly_auth.data.local.AuthDataStoreService
import com.superbeta.blibberly_auth.data.local.AuthDataStoreServiceImpl
import com.superbeta.blibberly_auth.data.remote.AuthRemoteService
import com.superbeta.blibberly_auth.data.remote.AuthRemoteServiceImpl
import com.superbeta.blibberly_auth.domain.AuthRepository
import com.superbeta.blibberly_auth.domain.AuthRepositoryImpl
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<CredentialManager> {
        CredentialManager.create(androidContext())
    }

    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://dxyahfscoumjwjuwlgje.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR4eWFoZnNjb3VtandqdXdsZ2plIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTY5MTUzOTMsImV4cCI6MjAzMjQ5MTM5M30.DqthAS5M1CSeBFQf87TAxv57eMCalxxiPAbRp_XQ8AE"
        ) {
            install(Postgrest)
            install(Auth)
            install(Storage)
        }
    }

    single <AuthRemoteService>{
        AuthRemoteServiceImpl(supabase = get())
    }

    single <AuthDataStoreService>{
        AuthDataStoreServiceImpl(userPreferencesDataStore = androidContext().userPreferencesDataStore)
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            context = androidContext(),
            credentialManager = get(),
            authRemoteService = get<AuthRemoteService>(),
            authDataStoreService = get()
        )
    }

    viewModel {
        AuthViewModel(authRepository = get<AuthRepository>())
    }
}