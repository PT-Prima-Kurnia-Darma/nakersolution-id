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
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneDeflectionItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeGirderItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneNdeWireRopeItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gantrycrane.GantryCraneUiState
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaCageItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaClampsItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaLoadTest
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaSteelWireRopeItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaSuspensionStructureItem
import com.nakersolutionid.nakersolutionid.features.report.paa.gondola.GondolaUiState
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

    private val _gantryCraneUiState = MutableStateFlow(GantryCraneUiState())
    val gantryCraneUiState: StateFlow<GantryCraneUiState> = _gantryCraneUiState.asStateFlow()

     private val _gondolaUiState = MutableStateFlow(GondolaUiState())
     val gondolaUiState: StateFlow<GondolaUiState> = _gondolaUiState.asStateFlow()

    // private val _mobileCraneUiState = MutableStateFlow()
    // val mobileCraneUiState: StateFlow<PAAUiState> = _mobileCraneUiState.asStateFlow()

    // private val _overheadCraneUiState = MutableStateFlow()
    // val overheadCraneUiState: StateFlow<PAAUiState> = _overheadCraneUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Forklift -> {
                    // TODO: Implement save logic for Forklift report
                }

                SubInspectionType.Mobil_Crane -> {}
                SubInspectionType.Overhead_Crane -> {}
                SubInspectionType.Gantry_Crane -> {
                    // TODO: Implement save logic for Gantry Crane report
                }

                SubInspectionType.Gondola -> {}
                else -> null
            }
        }
    }

    fun onUpdatePAAState(updater: (PAAUiState) -> PAAUiState) {
        _paaUiState.update(updater)
    }

    //region Forklift Logic (Existing)
    fun onForkliftReportDataChange(newReportData: ForkliftInspectionReport) {
        _forkliftUiState.update { it.copy(forkliftInspectionReport = newReportData) }
    }

    fun addNdeChainItem(item: ForkliftNdeChainItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentChainInspection = currentNde.liftingChainInspection
        val newItems = (currentChainInspection.items + item).toImmutableList()
        val updatedNde = currentNde.copy(
            liftingChainInspection = currentChainInspection.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun deleteNdeChainItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentChainInspection = currentNde.liftingChainInspection
        val newItems =
            currentChainInspection.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNde = currentNde.copy(
            liftingChainInspection = currentChainInspection.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun addNdeForkItem(item: ForkliftNdeForkItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentForkNdt = currentNde.forkNDT
        val newItems = (currentForkNdt.items + item).toImmutableList()
        val updatedNde = currentNde.copy(
            forkNDT = currentForkNdt.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun deleteNdeForkItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val currentForkNdt = currentNde.forkNDT
        val newItems =
            currentForkNdt.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNde = currentNde.copy(
            forkNDT = currentForkNdt.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun updateNdtType(ndtType: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentNde = currentReport.nonDestructiveExamination
        val updatedForkNdt = currentNde.forkNDT.copy(ndtType = ndtType)
        val updatedNde = currentNde.copy(forkNDT = updatedForkNdt)
        onForkliftReportDataChange(currentReport.copy(nonDestructiveExamination = updatedNde))
    }

    fun addLoadTestItem(item: ForkliftLoadTestItem) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentTesting = currentReport.testing
        val currentLoadTest = currentTesting.loadTest
        val newItems = (currentLoadTest.items + item).toImmutableList()
        val updatedTesting = currentTesting.copy(
            loadTest = currentLoadTest.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(testing = updatedTesting))
    }

    fun deleteLoadTestItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentTesting = currentReport.testing
        val currentLoadTest = currentTesting.loadTest
        val newItems =
            currentLoadTest.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedTesting = currentTesting.copy(
            loadTest = currentLoadTest.copy(items = newItems)
        )
        onForkliftReportDataChange(currentReport.copy(testing = updatedTesting))
    }

    fun addConclusionSummaryItem(item: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = (currentConclusion.summary + item).toImmutableList()
        onForkliftReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(summary = newItems)))
    }

    fun removeConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems =
            currentConclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onForkliftReportDataChange(currentReport.copy(conclusion = currentConclusion.copy(summary = newItems)))
    }

    fun addConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = (currentConclusion.recommendations + item).toImmutableList()
        onForkliftReportDataChange(
            currentReport.copy(
                conclusion = currentConclusion.copy(
                    recommendations = newItems
                )
            )
        )
    }

    fun removeConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val currentReport = _forkliftUiState.value.forkliftInspectionReport
        val currentConclusion = currentReport.conclusion
        val newItems = currentConclusion.recommendations.toMutableList().apply { removeAt(index) }
            .toImmutableList()
        onForkliftReportDataChange(
            currentReport.copy(
                conclusion = currentConclusion.copy(
                    recommendations = newItems
                )
            )
        )
    }
    //endregion

    //region Gantry Crane Logic (Existing)
    fun onGantryCraneReportDataChange(newReportData: GantryCraneInspectionReport) {
        _gantryCraneUiState.update { it.copy(gantryCraneInspectionReport = newReportData) }
    }

    fun addGantryNdeWireRopeItem(item: GantryCraneNdeWireRopeItem) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val wireRope = nde.wireRope
        val newItems = (wireRope.items + item).toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    wireRope = wireRope.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun deleteGantryNdeWireRopeItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val wireRope = nde.wireRope
        val newItems = wireRope.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    wireRope = wireRope.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun addGantryNdeGirderItem(item: GantryCraneNdeGirderItem) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val girder = nde.girder
        val newItems = (girder.items + item).toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    girder = girder.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun deleteGantryNdeGirderItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val nde = report.nonDestructiveExamination
        val girder = nde.girder
        val newItems = girder.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                nonDestructiveExamination = nde.copy(
                    girder = girder.copy(
                        items = newItems
                    )
                )
            )
        )
    }

    fun addGantryDeflectionItem(item: GantryCraneDeflectionItem) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val testing = report.testing
        val staticTest = testing.staticTest
        val newItems = (staticTest.deflectionMeasurement + item).toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                testing = testing.copy(
                    staticTest = staticTest.copy(
                        deflectionMeasurement = newItems
                    )
                )
            )
        )
    }

    fun deleteGantryDeflectionItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val testing = report.testing
        val staticTest = testing.staticTest
        val newItems = staticTest.deflectionMeasurement.toMutableList().apply { removeAt(index) }
            .toImmutableList()
        onGantryCraneReportDataChange(
            report.copy(
                testing = testing.copy(
                    staticTest = staticTest.copy(
                        deflectionMeasurement = newItems
                    )
                )
            )
        )
    }

    fun addGantryConclusionSummaryItem(item: String) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeGantryConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems =
            conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addGantryConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeGantryConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val report = _gantryCraneUiState.value.gantryCraneInspectionReport
        val conclusion = report.conclusion
        val newItems =
            conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGantryCraneReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion

    //region Gondola Logic (Existing)
    fun onGondolaReportDataChange(newReportData: GondolaInspectionReport) {
        _gondolaUiState.update { it.copy(gondolaInspectionReport = newReportData) }
    }

    fun addGondolaNdeSteelWireRopeItem(item: GondolaSteelWireRopeItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val steelWireRope = ndt.steelWireRope
        val newItems = (steelWireRope.items + item).toImmutableList()
        val updatedNdt = ndt.copy(steelWireRope = steelWireRope.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeSteelWireRopeItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val steelWireRope = ndt.steelWireRope
        val newItems = steelWireRope.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(steelWireRope = steelWireRope.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaNdeSuspensionStructureItem(item: GondolaSuspensionStructureItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val suspensionStructure = ndt.suspensionStructure
        val newItems = (suspensionStructure.items + item).toImmutableList()
        val updatedNdt = ndt.copy(suspensionStructure = suspensionStructure.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeSuspensionStructureItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val suspensionStructure = ndt.suspensionStructure
        val newItems = suspensionStructure.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(suspensionStructure = suspensionStructure.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaNdeCageItem(item: GondolaCageItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val cage = ndt.gondolaCage
        val newItems = (cage.items + item).toImmutableList()
        val updatedNdt = ndt.copy(gondolaCage = cage.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeCageItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val cage = ndt.gondolaCage
        val newItems = cage.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(gondolaCage = cage.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaNdeClampsItem(item: GondolaClampsItem) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val clamps = ndt.clamps
        val newItems = (clamps.items + item).toImmutableList()
        val updatedNdt = ndt.copy(clamps = clamps.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun deleteGondolaNdeClampsItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val ndt = report.nonDestructiveTesting
        val clamps = ndt.clamps
        val newItems = clamps.items.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedNdt = ndt.copy(clamps = clamps.copy(items = newItems))
        onGondolaReportDataChange(report.copy(nonDestructiveTesting = updatedNdt))
    }

    fun addGondolaDynamicLoadTestItem(item: GondolaLoadTest) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = (testing.dynamicLoad + item).toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(dynamicLoad = newItems)))
    }

    fun deleteGondolaDynamicLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = testing.dynamicLoad.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(dynamicLoad = newItems)))
    }

    fun addGondolaStaticLoadTestItem(item: GondolaLoadTest) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = (testing.staticLoad + item).toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(staticLoad = newItems)))
    }

    fun deleteGondolaStaticLoadTestItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val testing = report.testing
        val newItems = testing.staticLoad.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(testing = testing.copy(staticLoad = newItems)))
    }

    fun addGondolaConclusionSummaryItem(item: String) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeGondolaConclusionSummaryItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addGondolaConclusionRecommendationItem(item: String) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeGondolaConclusionRecommendationItem(index: Int) = viewModelScope.launch {
        val report = _gondolaUiState.value.gondolaInspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onGondolaReportDataChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion
}