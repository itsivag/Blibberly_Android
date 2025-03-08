package com.superbeta.profile_ops.report_ghost_block.domain

import android.util.Log
import com.superbeta.blibberly_user.CurrentUserDataProvider
import com.superbeta.profile_ops.report_ghost_block.data.ReportGhostBlockRemoteService
import com.superbeta.profile_ops.report_ghost_block.model.ReportGhostBlockDataModel
import java.text.DateFormat
import java.util.Calendar

class ReportGhostBlockRepoImpl(private val reportGhostBlockRemoteService: ReportGhostBlockRemoteService) : ReportGhostBlockRepo {
    override suspend fun registerProfileReport(report: String, reportedUser: String) {
        try {

            val time = Calendar.getInstance().time
            val formatter = DateFormat.getDateTimeInstance()
            val current = formatter.format(time)
            val reporter = CurrentUserDataProvider.getUserEmail()!!

            val profileReportModel = ReportGhostBlockDataModel(
                value = report,
                createAt = current.toString(),
                status = "",
                reporter = reporter,
                reportedUser = reportedUser
            )
            reportGhostBlockRemoteService.registerProfileReport(profileReportModel)
        } catch (e: Exception) {
            Log.e("ReportRepoImpl", "Error reporting the profile : $e")
        }
    }

    override suspend fun getProfileReportStatus() {
        TODO("Not yet implemented")
    }
}