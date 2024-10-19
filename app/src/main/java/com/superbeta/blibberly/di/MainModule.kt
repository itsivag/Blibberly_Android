package com.superbeta.blibberly.di

import com.superbeta.blibberly.user.data.remote.UserRemoteService
import com.superbeta.blibberly.user.data.remote.UserRemoteServiceImpl
import com.superbeta.blibberly.user.presentation.UserViewModel
import com.superbeta.blibberly.user.repo.MUserRepository
import com.superbeta.blibberly.user.repo.MUserRepositoryImpl
import com.superbeta.blibberly.utils.RoomInstanceProvider
import com.superbeta.blibberly_auth.utils.userPreferencesDataStore
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
    single {
        RoomInstanceProvider.getDb(androidContext()).userLocalDao()
    }

    single {
        NotificationRepoImpl()
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

    single<UserRemoteService> {
        UserRemoteServiceImpl(supabase = get())
    }

    single<MUserRepository> {
        MUserRepositoryImpl(
            db = get(),
            notificationRepo = get(),
            userPreferencesDataStore = androidContext().userPreferencesDataStore,
            userRemoteService = get()
        )
    }

    viewModel {
        UserViewModel(mUserRepository = get())
    }
}