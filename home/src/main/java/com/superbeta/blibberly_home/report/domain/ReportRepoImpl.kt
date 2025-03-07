package com.superbeta.blibberly_home.report.domain

import com.superbeta.blibberly_home.report.data.ReportRemoteService
import com.superbeta.blibberly_home.report.model.ProfileReportDataModel
import java.text.DateFormat
import java.util.Calendar

class ReportRepoImpl(private val reportRemoteService: ReportRemoteService) : ReportRepo {
    override suspend fun registerProfileReport(report: String, reportedUser: String) {
        val time = Calendar.getInstance().time
        val formatter = DateFormat.getDateTimeInstance()
        val current = formatter.format(time)
        val profileReportModel = ProfileReportDataModel(
            value = report,
            createAt = current.toString(),
            status = "",
            reporter = "",
            reportedUser = reportedUser
        )

        reportRemoteService.registerProfileReport(profileReportModel)
    }

    override suspend fun getProfileReportStatus() {
        TODO("Not yet implemented")
    }
}