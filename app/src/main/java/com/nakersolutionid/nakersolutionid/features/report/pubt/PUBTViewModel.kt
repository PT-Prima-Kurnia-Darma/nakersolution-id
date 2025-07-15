package com.nakersolutionid.nakersolutionid.features.report.pubt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralMeasurementResultItem
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PUBTViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _pubtUiState = MutableStateFlow(PUBTUiState())
    val pubtUiState: StateFlow<PUBTUiState> = _pubtUiState.asStateFlow()

    private val _generalUiState = MutableStateFlow(GeneralUiState())
    val generalUiState: StateFlow<GeneralUiState> = _generalUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.General_PUBT -> {
                    // TODO: Implement save logic for Machine report using _machineUiState.value
                }
                else -> {}
            }
        }
    }

    fun onGeneralReportChange(newReport: GeneralInspectionReport) {
        _generalUiState.update { it.copy(inspectionReport = newReport) }
    }

    fun addMeasurementResultItem(item: GeneralMeasurementResultItem) = viewModelScope.launch {
        val report = _generalUiState.value.inspectionReport
        val inspection = report.inspectionAndMeasurement
        val newItems = (inspection.measurementResultsTable + item).toImmutableList()
        onGeneralReportChange(report.copy(inspectionAndMeasurement = inspection.copy(measurementResultsTable = newItems)))
    }

    fun deleteMeasurementResultItem(index: Int) = viewModelScope.launch {
        val report = _generalUiState.value.inspectionReport
        val inspection = report.inspectionAndMeasurement
        val newItems = inspection.measurementResultsTable.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGeneralReportChange(report.copy(inspectionAndMeasurement = inspection.copy(measurementResultsTable = newItems)))
    }

    fun addConclusionSummary(item: String) = viewModelScope.launch {
        val report = _generalUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onGeneralReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeConclusionSummary(index: Int) = viewModelScope.launch {
        val report = _generalUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGeneralReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addConclusionRecommendation(item: String) = viewModelScope.launch {
        val report = _generalUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onGeneralReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeConclusionRecommendation(index: Int) = viewModelScope.launch {
        val report = _generalUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGeneralReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
}