package com.superbeta.profile_ops.report_ghost_block.data

import android.util.Log
import com.superbeta.profile_ops.report_ghost_block.model.ReportGhostBlockDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ReportGhostBlockRemoteServiceImpl(supabase: SupabaseClient) : ReportGhostBlockRemoteService {
    private val supabaseProfileReportsDb = supabase.from("ProfileReports")

    override suspend fun registerProfileReport(report: ReportGhostBlockDataModel) {
        try {
            supabaseProfileReportsDb.insert(report)
        } catch (e: Exception) {
            Log.e("ReportRemoteServiceImpl", " Failed to report : $e")
        }
    }

    override suspend fun getProfileReportStatus() {
        //TODO yet to implement
    }
}