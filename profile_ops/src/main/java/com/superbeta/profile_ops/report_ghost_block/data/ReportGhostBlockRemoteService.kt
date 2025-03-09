package com.superbeta.profile_ops.report_ghost_block.data

import com.superbeta.profile_ops.report_ghost_block.model.BlockDataModel
import com.superbeta.profile_ops.report_ghost_block.model.ReportDataModel

interface ReportGhostBlockRemoteService {
    //report
    suspend fun reportAndBlock(report: ReportDataModel, block: BlockDataModel)
    suspend fun getProfileReportStatus()

    //block
    suspend fun blockUser(blockDataModel: BlockDataModel)
    suspend fun getBlockedUsers(currentUserEmail : String): List<BlockDataModel>


}