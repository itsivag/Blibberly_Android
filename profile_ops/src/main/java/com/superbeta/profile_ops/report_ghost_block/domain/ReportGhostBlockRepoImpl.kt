package com.superbeta.profile_ops.report_ghost_block.domain

import android.util.Log
import com.superbeta.blibberly_user.CurrentUserDataProvider
import com.superbeta.profile_ops.report_ghost_block.data.ReportGhostBlockRemoteService
import com.superbeta.profile_ops.report_ghost_block.model.BlockDataModel
import com.superbeta.profile_ops.report_ghost_block.model.ReportDataModel

class ReportGhostBlockRepoImpl(private val reportGhostBlockRemoteService: ReportGhostBlockRemoteService) :
    ReportGhostBlockRepo {
    override suspend fun reportAndBlock(report: String, reportedAndBlockedUser: String) {
        try {

            val currentUser = CurrentUserDataProvider.getUserEmail()!!

            val reportDataModel = ReportDataModel(
                value = report,
                status = "",
                reporter = currentUser,
                reportedUser = reportedAndBlockedUser
            )

            val blockDataModel =
                BlockDataModel(blocker = currentUser, blockedUser = reportedAndBlockedUser)


            reportGhostBlockRemoteService.reportAndBlock(
                report = reportDataModel,
                block = blockDataModel
            )
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

    override suspend fun getBlockedUsers(): List<BlockDataModel> {
        try {
            val currentUser = CurrentUserDataProvider.getUserEmail()!!
            return reportGhostBlockRemoteService.getBlockedUsers(currentUser)
        } catch (e: Exception) {
            Log.e("ReportGhostBlockRepoImpl", "Error fetching blocked users : $e")
            return emptyList()
        }
    }
}
