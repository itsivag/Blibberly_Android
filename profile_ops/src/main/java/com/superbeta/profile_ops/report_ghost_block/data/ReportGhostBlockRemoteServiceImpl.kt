package com.superbeta.profile_ops.report_ghost_block.data

import android.util.Log
import com.superbeta.profile_ops.report_ghost_block.model.BlockDataModel
import com.superbeta.profile_ops.report_ghost_block.model.ReportDataModel
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ReportGhostBlockRemoteServiceImpl(supabase: SupabaseClient) : ReportGhostBlockRemoteService {
    private val supabaseReportedUsersDb = supabase.from("ReportedUsers")
    private val supabaseBlockedUsersDb = supabase.from("BlockedUsers")

    override suspend fun reportAndBlock(report: ReportDataModel, block: BlockDataModel) {
        //report
        supabaseReportedUsersDb.insert(report)
        //and block
        supabaseBlockedUsersDb.insert(block)
    }

    override suspend fun getProfileReportStatus() {
        //TODO yet to implement
    }

    override suspend fun blockUser(blockDataModel: BlockDataModel) {
        supabaseBlockedUsersDb.insert(blockDataModel)
    }

    override suspend fun getBlockedUsers(currentUserEmail: String): List<BlockDataModel> {
        val blockedUsers =
            supabaseBlockedUsersDb.select { filter { BlockDataModel::blocker eq currentUserEmail } }
                .decodeList<BlockDataModel>()
        Log.i("ReportGhostBlockRemoteServiceImpl", "Blcoked users : $blockedUsers")
        return blockedUsers
    }
}