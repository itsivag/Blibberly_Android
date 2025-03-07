package com.superbeta.blibberly_home.report.di

import com.superbeta.blibberly_home.BuildConfig
import com.superbeta.blibberly_home.report.data.ReportRemoteService
import com.superbeta.blibberly_home.report.data.ReportRemoteServiceImpl
import com.superbeta.blibberly_home.report.domain.ReportRepo
import com.superbeta.blibberly_home.report.domain.ReportRepoImpl
import com.superbeta.blibberly_home.report.viewModel.ReportViewModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val reportModule = module {
    single<SupabaseClient> {
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_DEBUG_URL,
            supabaseKey = BuildConfig.SUPABASE_DEBUG_KEY
        ) {
            install(Postgrest)
//            install(Auth)
//            install(Storage)
        }
    }
    single<ReportRemoteService> {
        ReportRemoteServiceImpl(get())
    }

    single<ReportRepo> {
        ReportRepoImpl(reportRemoteService = get())
    }

    viewModel {
        ReportViewModel(reportRepo = get())
    }
}