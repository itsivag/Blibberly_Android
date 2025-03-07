package com.superbeta.blibberly_home.report.domain

interface ReportRepo {
    suspend fun registerProfileReport(report: String, reportedUser: String)
    suspend fun getProfileReportStatus()

}