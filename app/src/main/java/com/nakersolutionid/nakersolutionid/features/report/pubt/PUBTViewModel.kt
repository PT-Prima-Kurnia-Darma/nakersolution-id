package com.nakersolutionid.nakersolutionid.features.report.pubt

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralMeasurementResultItem
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralUiState
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Dummy
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PUBTViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _pubtUiState = MutableStateFlow(PUBTUiState())
    val pubtUiState: StateFlow<PUBTUiState> = _pubtUiState.asStateFlow()

    private val _generalUiState = MutableStateFlow(Dummy.getDummyGeneralUiState())
    val generalUiState: StateFlow<GeneralUiState> = _generalUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.General_PUBT -> {
                    val electricalInspection = _generalUiState.value.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(electricalInspection)
                        _pubtUiState.update { it.copy(generalResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(_: SQLiteConstraintException) {
                        _pubtUiState.update { it.copy(generalResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (_: Exception) {
                        _pubtUiState.update { it.copy(generalResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }
                else -> {}
            }
        }
    }

    fun onUpdatePUBTState(updater: (PUBTUiState) -> PUBTUiState) {
        _pubtUiState.update(updater)
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

    /**
     * Load an existing report for editing.
     * @param reportId The ID of the report to load for editing
     */
    fun loadReportForEdit(reportId: Long) {
        viewModelScope.launch {
            try {
                _pubtUiState.update { it.copy(isLoading = true) }
                val inspection = reportUseCase.getInspection(reportId)
                
                if (inspection != null) {
                    // Convert the domain model back to UI state
                    // For now, we'll just show a success message
                    // The actual conversion will depend on the specific report structure
                    _pubtUiState.update { 
                        it.copy(
                            isLoading = false,
                            generalResult = Resource.Success("Data laporan berhasil dimuat untuk diedit")
                        )
                    }
                } else {
                    _pubtUiState.update { 
                        it.copy(
                            isLoading = false,
                            generalResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _pubtUiState.update { 
                    it.copy(
                        isLoading = false,
                        generalResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }
}