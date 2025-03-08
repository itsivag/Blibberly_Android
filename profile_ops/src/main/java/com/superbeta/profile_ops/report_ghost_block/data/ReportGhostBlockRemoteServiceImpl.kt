package com.superbeta.profile_ops.report_ghost_block.data

import com.superbeta.profile_ops.report_ghost_block.model.BlockDataModel
import com.superbeta.profile_ops.report_ghost_block.model.ReportDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ReportGhostBlockRemoteServiceImpl(supabase: SupabaseClient) : ReportGhostBlockRemoteService {
    private val supabaseReportedUsersDb = supabase.from("ReportedUsers")
    private val supabaseBlockedUsersDb = supabase.from("BlockedUsers")

    override suspend fun registerProfileReport(report: ReportDataModel) {
        supabaseReportedUsersDb.insert(report)
    }

    override suspend fun getProfileReportStatus() {
        //TODO yet to implement
    }

    override suspend fun blockUser(blockDataModel: BlockDataModel) {
        supabaseBlockedUsersDb.insert(blockDataModel)
    }
}