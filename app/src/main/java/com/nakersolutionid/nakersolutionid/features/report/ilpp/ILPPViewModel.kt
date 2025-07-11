package com.nakersolutionid.nakersolutionid.features.report.ilpp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpInternalViewItem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ILPPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ilppUiState = MutableStateFlow(ILPPUiState())
    val ilppUiState: StateFlow<ILPPUiState> = _ilppUiState.asStateFlow()

    private val _electricalUiState = MutableStateFlow(ElectricalUiState())
    val electricalUiState: StateFlow<ElectricalUiState> = _electricalUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Instalasi_Listrik -> {
                    // TODO: Implement save logic for Electrical Installation report using _electricalUiState.value
                }

                SubInspectionType.Instalasi_Penyalur_Petir -> {
                    // TODO: Implement save logic for Lightning Protection report
                }
                else -> {}
            }
        }
    }

    //region Electrical Installation Logic
    fun onElectricalReportChange(newReport: ElectricalInspectionReport) {
        _electricalUiState.update { it.copy(electricalInspectionReport = newReport) }
    }

    fun addSdpInternalViewItem(item: ElectricalSdpInternalViewItem) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val inspectionAndTesting = report.inspectionAndTesting
        val sdpVisual = inspectionAndTesting.sdpVisualInspection
        val newItems = (sdpVisual.internalViews + item).toImmutableList()
        val updatedSdpVisual = sdpVisual.copy(internalViews = newItems)
        val updatedInspectionAndTesting = inspectionAndTesting.copy(sdpVisualInspection = updatedSdpVisual)
        onElectricalReportChange(report.copy(inspectionAndTesting = updatedInspectionAndTesting))
    }

    fun deleteSdpInternalViewItem(index: Int) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val inspectionAndTesting = report.inspectionAndTesting
        val sdpVisual = inspectionAndTesting.sdpVisualInspection
        val newItems = sdpVisual.internalViews.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedSdpVisual = sdpVisual.copy(internalViews = newItems)
        val updatedInspectionAndTesting = inspectionAndTesting.copy(sdpVisualInspection = updatedSdpVisual)
        onElectricalReportChange(report.copy(inspectionAndTesting = updatedInspectionAndTesting))
    }

    fun addElectricalFinding(item: String) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.findings + item).toImmutableList()
        onElectricalReportChange(report.copy(conclusion = conclusion.copy(findings = newItems)))
    }

    fun removeElectricalFinding(index: Int) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.findings.toMutableList().apply { removeAt(index) }.toImmutableList()
        onElectricalReportChange(report.copy(conclusion = conclusion.copy(findings = newItems)))
    }

    fun addElectricalSummary(item: String) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onElectricalReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeElectricalSummary(index: Int) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onElectricalReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addElectricalRecommendation(item: String) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onElectricalReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeElectricalRecommendation(index: Int) = viewModelScope.launch {
        val report = _electricalUiState.value.electricalInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onElectricalReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion
}