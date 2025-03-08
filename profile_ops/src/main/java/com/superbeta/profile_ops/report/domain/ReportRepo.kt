package com.superbeta.profile_ops.report.domain

interface ReportRepo {
    suspend fun registerProfileReport(report: String, reportedUser: String)
    suspend fun getProfileReportStatus()

}