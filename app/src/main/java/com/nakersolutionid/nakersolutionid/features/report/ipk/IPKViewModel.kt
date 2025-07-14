package com.nakersolutionid.nakersolutionid.features.report.ipk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionHydrantOperationalTestItem
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionPumpFunctionTestItem
import com.nakersolutionid.nakersolutionid.features.report.ipk.fireprotection.FireProtectionUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class IPKViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ipkUiState = MutableStateFlow(IPKUiState())
    val ipkUiState: StateFlow<IPKUiState> = _ipkUiState.asStateFlow()

    private val _fireProtectionUiState = MutableStateFlow(FireProtectionUiState())
    val fireProtectionUiState: StateFlow<FireProtectionUiState> = _fireProtectionUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Fire_Protection -> {
                    // TODO: Implement save logic for Machine report using _machineUiState.value
                }
                else -> {}
            }
        }
    }

    fun onFireProtectionReportChange(newReport: FireProtectionInspectionReport) {
        _fireProtectionUiState.update { it.copy(inspectionReport = newReport) }
    }

    fun addPumpFunctionTestItem(item: FireProtectionPumpFunctionTestItem) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = (report.pumpFunctionTest + item).toImmutableList()
        onFireProtectionReportChange(report.copy(pumpFunctionTest = newItems))
    }

    fun deletePumpFunctionTestItem(index: Int) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = report.pumpFunctionTest.toMutableList().apply { removeAt(index) }.toImmutableList()
        onFireProtectionReportChange(report.copy(pumpFunctionTest = newItems))
    }

    fun addHydrantOperationalTestItem(item: FireProtectionHydrantOperationalTestItem) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = (report.hydrantOperationalTest + item).toImmutableList()
        onFireProtectionReportChange(report.copy(hydrantOperationalTest = newItems))
    }

    fun deleteHydrantOperationalTestItem(index: Int) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val newItems = report.hydrantOperationalTest.toMutableList().apply { removeAt(index) }.toImmutableList()
        onFireProtectionReportChange(report.copy(hydrantOperationalTest = newItems))
    }

    fun addConclusionRecommendation(item: String) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onFireProtectionReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeConclusionRecommendation(index: Int) = viewModelScope.launch {
        val report = _fireProtectionUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onFireProtectionReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
}