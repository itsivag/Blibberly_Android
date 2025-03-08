package com.superbeta.profile_ops.report_ghost_block.domain

interface ReportGhostBlockRepo {

    //report
    suspend fun registerProfileReport(report: String, reportedUser: String)
    suspend fun getProfileReportStatus()

    //block
    suspend fun blockUser(blockedUser: String)
}