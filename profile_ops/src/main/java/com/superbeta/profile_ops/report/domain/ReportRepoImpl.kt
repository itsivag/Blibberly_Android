package com.superbeta.profile_ops.report.domain

import android.util.Log
import com.superbeta.blibberly_user.CurrentUserDataProvider
import com.superbeta.profile_ops.report.data.ReportRemoteService
import com.superbeta.profile_ops.report.model.ProfileReportDataModel
import java.text.DateFormat
import java.util.Calendar

class ReportRepoImpl(private val reportRemoteService: ReportRemoteService) : ReportRepo {
    override suspend fun registerProfileReport(report: String, reportedUser: String) {
        try {

            val time = Calendar.getInstance().time
            val formatter = DateFormat.getDateTimeInstance()
            val current = formatter.format(time)
            val reporter = CurrentUserDataProvider.getUserEmail()!!

            val profileReportModel = ProfileReportDataModel(
                value = report,
                createAt = current.toString(),
                status = "",
                reporter = reporter,
                reportedUser = reportedUser
            )
            reportRemoteService.registerProfileReport(profileReportModel)
        } catch (e: Exception) {
            Log.e("ReportRepoImpl", "Error reporting the profile : $e")
        }
    }

    override suspend fun getProfileReportStatus() {
        TODO("Not yet implemented")
    }
}