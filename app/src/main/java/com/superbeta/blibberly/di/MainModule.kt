package com.superbeta.blibberly.di

import com.superbeta.blibberly.BuildConfig
import com.superbeta.blibberly.user.data.local.UserLocalDao
import com.superbeta.blibberly.user.data.remote.UserRemoteService
import com.superbeta.blibberly.user.data.remote.UserRemoteServiceImpl
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.user.repo.MUserRepository
import com.superbeta.blibberly.user.repo.MUserRepositoryImpl
import com.superbeta.blibberly.utils.RoomInstanceProvider
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
import com.superbeta.blibberly_chat.notification.NotificationRepo
import com.superbeta.blibberly_chat.notification.NotificationRepoImpl
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single<UserLocalDao> {
        RoomInstanceProvider.getDb(androidContext()).userLocalDao()
    }

    single<NotificationRepo> {
        NotificationRepoImpl()
    }

    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl =  BuildConfig.SUPABASE_DEBUG_URL,
            supabaseKey = BuildConfig.SUPABASE_DEBUG_KEY
        ) {
            install(Postgrest)
            install(Auth)
            install(Storage)
        }
    }

    single<UserRemoteService> {
        UserRemoteServiceImpl(supabase = get())
    }

    single<MUserRepository> {
        MUserRepositoryImpl(
            db = get(),
            notificationRepo = get(),
//            userPreferencesDataStore = androidContext().userPreferencesDataStore,
            userRemoteService = get()
        )
    }

    viewModel {
        UserViewModel(mUserRepository = get())
    }
}