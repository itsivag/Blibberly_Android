package com.superbeta.blibberly_home.report.data

import com.superbeta.blibberly_home.report.model.ProfileReportDataModel

interface ReportRemoteService {
    suspend fun registerProfileReport(report: ProfileReportDataModel)
    suspend fun getProfileReportStatus()
}