package com.superbeta.profile_ops.report_ghost_block.di

import com.superbeta.profile_ops.BuildConfig
import com.superbeta.profile_ops.report_ghost_block.data.ReportGhostBlockRemoteService
import com.superbeta.profile_ops.report_ghost_block.data.ReportGhostBlockRemoteServiceImpl
import com.superbeta.profile_ops.report_ghost_block.domain.ReportGhostBlockRepo
import com.superbeta.profile_ops.report_ghost_block.domain.ReportGhostBlockRepoImpl
import com.superbeta.profile_ops.report_ghost_block.presentation.viewModel.ReportGhostBlockViewModel
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
    single<ReportGhostBlockRemoteService> {
        ReportGhostBlockRemoteServiceImpl(get())
    }

    single<ReportGhostBlockRepo> {
        ReportGhostBlockRepoImpl(reportGhostBlockRemoteService = get())
    }

    viewModel {
        ReportGhostBlockViewModel(reportGhostBlockRepo = get())
    }
}