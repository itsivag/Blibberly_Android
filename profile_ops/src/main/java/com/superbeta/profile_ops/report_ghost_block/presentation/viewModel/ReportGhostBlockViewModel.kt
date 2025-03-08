package com.superbeta.profile_ops.report_ghost_block.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.profile_ops.report_ghost_block.domain.ReportGhostBlockRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ReportGhostBlockViewModel(private val reportGhostBlockRepo: ReportGhostBlockRepo) : ViewModel() {
    fun registerProfileReport(report: String, reportedUser: String) {
        viewModelScope.launch(IO) {
            reportGhostBlockRepo.registerProfileReport(report, reportedUser)
        }
    }

    fun getProfileReportStatus() {
        viewModelScope.launch(IO) {
            reportGhostBlockRepo.getProfileReportStatus()
        }
    }


}