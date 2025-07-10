package com.nakersolutionid.nakersolutionid.features.report.paa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftLoadTestItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeChainItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftNdeForkItem
import com.nakersolutionid.nakersolutionid.features.report.paa.forklift.ForkliftUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PAAViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _paaUiState = MutableStateFlow(PAAUiState())
    val paaUiState: StateFlow<PAAUiState> = _paaUiState.asStateFlow()

     private val _forkliftUiState = MutableStateFlow(ForkliftUiState())
     val forkliftUiState: StateFlow<ForkliftUiState> = _forkliftUiState.asStateFlow()

    // private val _gantryCraneUiState = MutableStateFlow()
    // val gantryCraneUiState: StateFlow<PAAUiState> = _gantryCraneUiState.asStateFlow()

    // private val _gondolaUiState = MutableStateFlow()
    // val gondolaUiState: StateFlow<PAAUiState> = _gondolaUiState.asStateFlow()

    // private val _mobileCraneUiState = MutableStateFlow()
    // val mobileCraneUiState: StateFlow<PAAUiState> = _mobileCraneUiState.asStateFlow()

    // private val _overheadCraneUiState = MutableStateFlow()
    // val overheadCraneUiState: StateFlow<PAAUiState> = _overheadCraneUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Forklift -> {}
                SubInspectionType.Bulldozer -> {}
                SubInspectionType.Excavator -> {}
                SubInspectionType.Wheel_Loader -> {}
                SubInspectionType.Farm_Tractor -> {}
                SubInspectionType.Motor_Grader -> {}
                SubInspectionType.Mobil_Crane -> {}
                SubInspectionType.Hoist_Crane -> {}
                SubInspectionType.Tower_Crane -> {}
                SubInspectionType.Truck_Crane -> {}
                SubInspectionType.Overhead_Crane -> {}
                SubInspectionType.Gantry_Crane -> {}
                SubInspectionType.Gondola -> {}
                SubInspectionType.Jib_Crane -> {}
                else -> null
            }
        }
    }

    fun onUpdatePAAState(updater: (PAAUiState) -> PAAUiState) {
        _paaUiState.update(updater)
    }

    fun onReportDataChange(newReportData: ForkliftInspectionReport) {
        _forkliftUiState.update { it.copy(forkliftInspectionReport = newReportData) }
    }

    // Handlers for NDE Chain Inspection List
    fun addNdeChainItem(item: ForkliftNdeChainItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentChainInspection = currentNde.liftingChainInspection
        val newItems = (currentChainInspection.items + item).toImmutableList()
        val updatedNde = currentNde.copy(
            liftingChainInspection = currentChainInspection.copy(items = newItems)
        )
        onReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun deleteNdeChainItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentChainInspection = currentNde.liftingChainInspection
        val newItems = currentChainInspection.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNde = currentNde.copy(
            liftingChainInspection = currentChainInspection.copy(items = newItems)
        )
        onReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }


    // Handlers for NDE Fork List
    fun addNdeForkItem(item: ForkliftNdeForkItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentForkNdt = currentNde.forkNDT
        val newItems = (currentForkNdt.items + item).toImmutableList()
        val updatedNde = currentNde.copy(
            forkNDT = currentForkNdt.copy(items = newItems)
        )
        onReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun deleteNdeForkItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentForkNdt = currentNde.forkNDT
        val newItems = currentForkNdt.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNde = currentNde.copy(
            forkNDT = currentForkNdt.copy(items = newItems)
        )
        onReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun updateNdtType(ndtType: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val updatedForkNdt = currentNde.forkNDT.copy(ndtType = ndtType)
        val updatedNde = currentNde.copy(forkNDT = updatedForkNdt)
        onReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    // Handlers for Load Test List
    fun addLoadTestItem(item: ForkliftLoadTestItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentTesting = currentReport.testing
        val currentLoadTest = currentTesting.loadTest
        val newItems = (currentLoadTest.items + item).toImmutableList()
        val updatedTesting = currentTesting.copy(
            loadTest = currentLoadTest.copy(items = newItems)
        )
        onReportDataChange(currentReport.copy(testing = updatedTesting))
    }

    fun deleteLoadTestItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentTesting = currentReport.testing
        val currentLoadTest = currentTesting.loadTest
        val newItems = currentLoadTest.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedTesting = currentTesting.copy(
            loadTest = currentLoadTest.copy(items = newItems)
        )
        onReportDataChange(currentReport.copy(testing = updatedTesting))
    }

    // Handlers for Conclusion Summary List
    fun addConclusionSummaryItem(item: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = (currentConclusion.summary + item).toImmutableList()
        onReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(summary = newItems)))
    }

    fun removeConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = currentConclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(summary = newItems)))
    }

    // Handlers for Conclusion Recommendations List
    fun addConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = (currentConclusion.recommendations + item).toImmutableList()
        onReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(recommendations = newItems)))
    }

    fun removeConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = currentConclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(recommendations = newItems)))
    }
}