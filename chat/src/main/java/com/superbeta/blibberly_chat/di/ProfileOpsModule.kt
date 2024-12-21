package com.superbeta.blibberly_chat.di

import com.blibberly.profile_ops.data.local.ProfileOpsDao
import com.blibberly.profile_ops.data.remote.ProfileOpsRemoteService
import com.blibberly.profile_ops.data.remote.ProfileOpsRemoteServiceImpl
import com.blibberly.profile_ops.domain.ProfileOpsRepo
import com.blibberly.profile_ops.domain.ProfileOpsRepoImpl
import com.blibberly.profile_ops.presentation.viewmodel.ProfileOpsViewModel
import com.superbeta.blibberly_chat.data.local.BlibberlyRoomInstanceProvider
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileOpsModule = module {

    single<ProfileOpsDao> {
        BlibberlyRoomInstanceProvider.getDatabase(androidContext()).ProfileOpsDao()
    }

    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = "https://dxyahfscoumjwjuwlgje.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImR4eWFoZnNjb3VtandqdXdsZ2plIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTY5MTUzOTMsImV4cCI6MjAzMjQ5MTM5M30.DqthAS5M1CSeBFQf87TAxv57eMCalxxiPAbRp_XQ8AE"
        ) {
            install(Postgrest)
            install(Auth)
//            install(Storage)
        }
    }


    single<ProfileOpsRemoteService> {
        ProfileOpsRemoteServiceImpl(supabase = get())
    }

    single<ProfileOpsRepo> {
        ProfileOpsRepoImpl(db = get(), profileOpsRemoteService = get())
    }

    viewModel {
        ProfileOpsViewModel(profileOpsRepo = get())
    }
}