package com.nakersolutionid.nakersolutionid.features.report.ptp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PTPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ptpUiState = MutableStateFlow(PTPUiState())
    val ptpUiState: StateFlow<PTPUiState> = _ptpUiState.asStateFlow()

    private val _machineUiState = MutableStateFlow(ProductionMachineUiState())
    val machineUiState: StateFlow<ProductionMachineUiState> = _machineUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Machine -> {
                    // TODO: Implement save logic for Machine report using _machineUiState.value
                }

                SubInspectionType.Motor_Diesel -> {
                    // TODO: Implement save logic for Motor Diesel report
                }
                else -> {}
            }
        }
    }

    fun onMachineReportChange(newReport: ProductionMachineInspectionReport) {
        _machineUiState.update { it.copy(inspectionReport = newReport) }
    }

    fun addMachineConclusionSummary(item: String) = viewModelScope.launch {
        val report = _machineUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onMachineReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeMachineConclusionSummary(index: Int) = viewModelScope.launch {
        val report = _machineUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMachineReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addMachineConclusionRequirement(item: String) = viewModelScope.launch {
        val report = _machineUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.requirements + item).toImmutableList()
        onMachineReportChange(report.copy(conclusion = conclusion.copy(requirements = newItems)))
    }

    fun removeMachineConclusionRequirement(index: Int) = viewModelScope.launch {
        val report = _machineUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.requirements.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMachineReportChange(report.copy(conclusion = conclusion.copy(requirements = newItems)))
    }
}