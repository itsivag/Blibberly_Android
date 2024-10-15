package com.superbeta.blibberly_auth.di

import androidx.credentials.CredentialManager
import com.superbeta.blibberly_auth.domain.AuthRepository
import com.superbeta.blibberly_auth.domain.AuthRepositoryImpl
import com.superbeta.blibberly_auth.presentation.viewmodel.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authModule = module {
    single<CredentialManager> {
        CredentialManager.create(androidContext())
    }
    single<AuthRepository> {
        AuthRepositoryImpl(context = androidContext(), credentialManager = get())
    }

    viewModel {
        AuthViewModel(authRepository = get())
    }
}