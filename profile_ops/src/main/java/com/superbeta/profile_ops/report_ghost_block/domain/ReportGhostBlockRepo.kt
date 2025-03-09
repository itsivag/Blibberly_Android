package com.superbeta.profile_ops.report_ghost_block.domain

import com.superbeta.profile_ops.report_ghost_block.model.BlockDataModel

interface ReportGhostBlockRepo {

    //report
    suspend fun reportAndBlock(report: String, reportedAndBlockedUser: String)
    suspend fun getProfileReportStatus()

    //block
    suspend fun blockUser(blockedUser: String)
    suspend fun getBlockedUsers(): List<BlockDataModel>
}