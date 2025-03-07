package com.superbeta.blibberly_home.report.data

import android.util.Log
import com.superbeta.blibberly_home.report.model.ProfileReportDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ReportRemoteServiceImpl(supabase: SupabaseClient) : ReportRemoteService {
    private val supabaseProfileReportsDb = supabase.from("ProfileReports")

    override suspend fun registerProfileReport(report: ProfileReportDataModel) {
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