package com.superbeta.profile_ops.report_ghost_block.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superbeta.profile_ops.report_ghost_block.domain.ReportGhostBlockRepo
import com.superbeta.profile_ops.report_ghost_block.model.BlockDataModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReportGhostBlockViewModel(private val reportGhostBlockRepo: ReportGhostBlockRepo) :
    ViewModel() {
    private val _blockedUserState = MutableStateFlow<List<BlockDataModel>>(emptyList())
    val blockedUserState: StateFlow<List<BlockDataModel>> = _blockedUserState.asStateFlow()

    fun registerProfileReport(report: String, reportedUser: String) {
        viewModelScope.launch(IO) {
            reportGhostBlockRepo.reportAndBlock(report, reportedUser)
        }
    }

    fun getProfileReportStatus() {
        viewModelScope.launch(IO) {
            reportGhostBlockRepo.getProfileReportStatus()
        }
    }

    fun blockUser(blockedUserEmail: String) {
        viewModelScope.launch(IO) {
            reportGhostBlockRepo.blockUser(blockedUserEmail)
        }
    }

    fun getBlockedUsers() {
        viewModelScope.launch(IO) {
            _blockedUserState.value = reportGhostBlockRepo.getBlockedUsers()
        }
    }

}