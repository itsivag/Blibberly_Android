package com.superbeta.profile_ops.report_ghost_block.domain

import android.util.Log
import com.superbeta.blibberly_user.CurrentUserDataProvider
import com.superbeta.profile_ops.report_ghost_block.data.ReportGhostBlockRemoteService
import com.superbeta.profile_ops.report_ghost_block.model.BlockDataModel
import com.superbeta.profile_ops.report_ghost_block.model.ReportDataModel

class ReportGhostBlockRepoImpl(private val reportGhostBlockRemoteService: ReportGhostBlockRemoteService) :
    ReportGhostBlockRepo {
    override suspend fun registerProfileReport(report: String, reportedUser: String) {
        try {

            val reporter = CurrentUserDataProvider.getUserEmail()!!

            val profileReportModel = ReportDataModel(
                value = report,
//                createAt = current.toString(),
                status = "",
                reporter = reporter,
                reportedUser = reportedUser
            )
            reportGhostBlockRemoteService.registerProfileReport(profileReportModel)
        } catch (e: Exception) {
            Log.e("ReportGhostBlockRepoImpl", "Error reporting the profile : $e")
        }
    }

    override suspend fun getProfileReportStatus() {
        TODO("Not yet implemented")
    }

    override suspend fun blockUser(blockedUser: String) {
        try {

            val blockDataModel = BlockDataModel(
                blocker = CurrentUserDataProvider.getUserEmail()!!,
                blockedUser = blockedUser
            )
            reportGhostBlockRemoteService.blockUser(blockDataModel)
        } catch (e: Exception) {
            Log.e("ReportGhostBlockRepoImpl", "Error blocking the profile : $e")
        }
    }
}
