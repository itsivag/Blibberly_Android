package com.superbeta.profile_ops.report_ghost_block.domain

interface ReportGhostBlockRepo {
    suspend fun registerProfileReport(report: String, reportedUser: String)
    suspend fun getProfileReportStatus()

}