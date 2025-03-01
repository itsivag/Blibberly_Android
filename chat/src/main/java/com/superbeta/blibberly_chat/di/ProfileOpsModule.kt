package com.superbeta.blibberly_chat.di

import com.superbeta.profile_ops.data.remote.ProfileOpsRemoteService
import com.superbeta.profile_ops.data.remote.ProfileOpsRemoteServiceImpl
import com.superbeta.profile_ops.domain.ProfileOpsRepo
import com.superbeta.profile_ops.domain.ProfileOpsRepoImpl
import com.superbeta.profile_ops.presentation.viewmodel.ProfileOpsViewModel
import com.superbeta.blibberly_chat.BuildConfig
import com.superbeta.blibberly_chat.data.local.BlibberlyRoomInstanceProvider
import com.superbeta.profile_ops.data.local.ProfileOpsDao
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
            supabaseUrl = BuildConfig.SUPABASE_DEBUG_URL,
            supabaseKey = BuildConfig.SUPABASE_DEBUG_KEY
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