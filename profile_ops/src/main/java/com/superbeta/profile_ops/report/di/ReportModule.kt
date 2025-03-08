package com.superbeta.profile_ops.report.di

import com.superbeta.profile_ops.BuildConfig
import com.superbeta.profile_ops.report.data.ReportRemoteService
import com.superbeta.profile_ops.report.data.ReportRemoteServiceImpl
import com.superbeta.profile_ops.report.domain.ReportRepo
import com.superbeta.profile_ops.report.domain.ReportRepoImpl
import com.superbeta.profile_ops.report.presentation.viewModel.ReportViewModel
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