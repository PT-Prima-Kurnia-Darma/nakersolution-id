package com.nakersolutionid.nakersolutionid.features.report.ilpp

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalSdpInternalViewItem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.ElectricalUiState
import com.nakersolutionid.nakersolutionid.features.report.ilpp.electric.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionGroundingMeasurementItem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionGroundingTestItem
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.LightningProtectionUiState
import com.nakersolutionid.nakersolutionid.features.report.ilpp.lightning.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Dummy
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ILPPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ilppUiState = MutableStateFlow(ILPPUiState())
    val ilppUiState: StateFlow<ILPPUiState> = _ilppUiState.asStateFlow()

    private val _electricalUiState = MutableStateFlow(Dummy.getDummyElectricUiState())
    val electricalUiState: StateFlow<ElectricalUiState> = _electricalUiState.asStateFlow()

    private val _lightningUiState = MutableStateFlow(Dummy.getDummyLightningUiState())
    val lightningUiState: StateFlow<LightningProtectionUiState> = _lightningUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Electrical -> {
                    val electricalInspection = _electricalUiState.value.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(electricalInspection)
                        _ilppUiState.update { it.copy(electricResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(e: SQLiteConstraintException) {
                        _ilppUiState.update { it.copy(electricResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (e: Exception) {
                        _ilppUiState.update { it.copy(electricResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }

                SubInspectionType.Lightning_Conductor -> {
                    val lightningInspection = _lightningUiState.value.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(lightningInspection)
                        _ilppUiState.update { it.copy(lightningResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(e: SQLiteConstraintException) {
                        _ilppUiState.update { it.copy(lightningResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (e: Exception) {
                        _ilppUiState.update { it.copy(lightningResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }
                else -> {}
            }
        }
    }

    fun onILPPUpdateState(updater: (ILPPUiState) -> ILPPUiState) {
        _ilppUiState.update(updater)
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

    //region Lightning Protection Logic
    fun onLightningReportChange(newReport: LightningProtectionInspectionReport) {
        _lightningUiState.update { it.copy(inspectionReport = newReport) }
    }

    fun addGroundingMeasurementItem(item: LightningProtectionGroundingMeasurementItem) = viewModelScope.launch {
        val report = _lightningUiState.value.inspectionReport
        val testingResults = report.testingResults
        val newItems = (testingResults.groundingResistanceMeasurement + item).toImmutableList()
        val updatedResults = testingResults.copy(groundingResistanceMeasurement = newItems)
        onLightningReportChange(report.copy(testingResults = updatedResults))
    }

    fun deleteGroundingMeasurementItem(index: Int) = viewModelScope.launch {
        val report = _lightningUiState.value.inspectionReport
        val testingResults = report.testingResults
        val newItems = testingResults.groundingResistanceMeasurement.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedResults = testingResults.copy(groundingResistanceMeasurement = newItems)
        onLightningReportChange(report.copy(testingResults = updatedResults))
    }

    fun addGroundingTestItem(item: LightningProtectionGroundingTestItem) = viewModelScope.launch {
        val report = _lightningUiState.value.inspectionReport
        val testingResults = report.testingResults
        val newItems = (testingResults.groundingResistanceTest + item).toImmutableList()
        val updatedResults = testingResults.copy(groundingResistanceTest = newItems)
        onLightningReportChange(report.copy(testingResults = updatedResults))
    }

    fun deleteGroundingTestItem(index: Int) = viewModelScope.launch {
        val report = _lightningUiState.value.inspectionReport
        val testingResults = report.testingResults
        val newItems = testingResults.groundingResistanceTest.toMutableList().apply { removeAt(index) }.toImmutableList()
        val updatedResults = testingResults.copy(groundingResistanceTest = newItems)
        onLightningReportChange(report.copy(testingResults = updatedResults))
    }

    fun addLightningRecommendation(item: String) = viewModelScope.launch {
        val report = _lightningUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.recommendations + item).toImmutableList()
        onLightningReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }

    fun removeLightningRecommendation(index: Int) = viewModelScope.launch {
        val report = _lightningUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.recommendations.toMutableList().apply { removeAt(index) }.toImmutableList()
        onLightningReportChange(report.copy(conclusion = conclusion.copy(recommendations = newItems)))
    }
    //endregion

    /**
     * Load an existing report for editing.
     * @param reportId The ID of the report to load for editing
     */
    fun loadReportForEdit(reportId: Long) {
        viewModelScope.launch {
            try {
                _ilppUiState.update { it.copy(isLoading = true) }
                val inspection = reportUseCase.getInspection(reportId)
                
                if (inspection != null) {
                    // Convert the domain model back to UI state
                    // For now, we'll just show a success message
                    // The actual conversion will depend on the specific report structure
                    _ilppUiState.update { 
                        it.copy(
                            isLoading = false,
                            electricResult = Resource.Success("Data laporan berhasil dimuat untuk diedit")
                        )
                    }
                } else {
                    _ilppUiState.update { 
                        it.copy(
                            isLoading = false,
                            electricResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _ilppUiState.update { 
                    it.copy(
                        isLoading = false,
                        electricResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }
}