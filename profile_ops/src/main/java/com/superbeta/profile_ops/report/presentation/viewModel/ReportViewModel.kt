package com.superbeta.profile_ops.report.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.profile_ops.report.domain.ReportRepo
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class ReportViewModel(private val reportRepo: ReportRepo) : ViewModel() {
    fun registerProfileReport(report: String, reportedUser: String) {
        viewModelScope.launch(IO) {
            reportRepo.registerProfileReport(report, reportedUser)
        }
    }

    fun getProfileReportStatus() {
        viewModelScope.launch(IO) {
            reportRepo.getProfileReportStatus()
        }
    }


}