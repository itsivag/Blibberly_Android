package com.superbeta.blibberly_auth.di

import androidx.credentials.CredentialManager
import com.superbeta.blibberly_auth.BuildConfig
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
            supabaseUrl = BuildConfig.SUPABASE_DEBUG_URL,
            supabaseKey = BuildConfig.SUPABASE_DEBUG_KEY
        ) {
            install(Postgrest)
            install(Auth)
            install(Storage)
        }
    }

    single<AuthRemoteService> {
        AuthRemoteServiceImpl(supabase = get())
    }

    single<AuthDataStoreService> {
        AuthDataStoreServiceImpl(userPreferencesDataStore = androidContext().userPreferencesDataStore)
    }

    single<AuthRepository> {
        AuthRepositoryImpl(
            context = androidContext(),
            credentialManager = get(),
            authRemoteService = get<AuthRemoteService>(),
//            authDataStoreService = get()
        )
    }

    viewModel {
        AuthViewModel(authRepository = get<AuthRepository>())
    }
}