package com.superbeta.profile_ops.report_ghost_block.data

import com.superbeta.profile_ops.report_ghost_block.model.ReportGhostBlockDataModel

interface ReportGhostBlockRemoteService {
    suspend fun registerProfileReport(report: ReportGhostBlockDataModel)
    suspend fun getProfileReportStatus()
}