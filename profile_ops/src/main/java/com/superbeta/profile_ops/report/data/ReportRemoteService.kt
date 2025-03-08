package com.superbeta.profile_ops.report.data

import com.superbeta.profile_ops.report.model.ProfileReportDataModel

interface ReportRemoteService {
    suspend fun registerProfileReport(report: ProfileReportDataModel)
    suspend fun getProfileReportStatus()
}