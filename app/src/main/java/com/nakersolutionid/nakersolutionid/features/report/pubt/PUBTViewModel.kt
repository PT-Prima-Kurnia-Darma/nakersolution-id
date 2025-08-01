package com.nakersolutionid.nakersolutionid.features.report.pubt

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralMeasurementResultItem
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.GeneralUiState
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.toGeneralUiState
import com.nakersolutionid.nakersolutionid.features.report.pubt.general.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class MeasurementResultType {
    TOP_HEAD,
    SHELL,
    BUTTON_HEAD
}

class PUBTViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _pubtUiState = MutableStateFlow(PUBTUiState())
    val pubtUiState: StateFlow<PUBTUiState> = _pubtUiState.asStateFlow()

//    private val _generalUiState = MutableStateFlow(GeneralUiState.createDummyGeneralUiState())
    private val _generalUiState = MutableStateFlow(GeneralUiState())
    val generalUiState: StateFlow<GeneralUiState> = _generalUiState.asStateFlow()

    // Store the current report ID for editing
    private var currentReportId: Long? = null
    private var isSynced = false

    fun onGetMLResult(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.General_PUBT -> {
                    val inspection = _generalUiState.value.toInspectionWithDetailsDomain(currentTime, _pubtUiState.value.editMode, currentReportId)
                    collectMlResult(inspection)
                }
                else -> {}
            }
        }
    }

    private suspend fun collectMlResult(inspection: InspectionWithDetailsDomain) {
        reportUseCase.getMLResult(inspection).collect { data ->
            when (data) {
                is Resource.Error -> {
                    onUpdatePUBTState { it.copy(mlResult = data.message, mlLoading = false) }
                }
                is Resource.Loading -> onUpdatePUBTState { it.copy(mlLoading = true) }
                is Resource.Success -> {
                    val conclusion = data.data?.conclusion ?: ""
                    val recommendation = data.data?.recommendation?.toImmutableList() ?: persistentListOf()
                    when (inspection.inspection.subInspectionType) {
                        SubInspectionType.General_PUBT -> {
                            val report = _generalUiState.value.inspectionReport
                            val updatedConclusion = report.conclusion.copy(
                                summary = persistentListOf(conclusion),
                                recommendations = recommendation
                            )
                            onGeneralReportChange(report.copy(conclusion = updatedConclusion))
                        }
                        else -> {}
                    }
                    onUpdatePUBTState { it.copy(mlLoading = false) }
                }
            }
        }
    }

    fun onCopyClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.General_PUBT -> {
                    val electricalInspection = _generalUiState.value.toInspectionWithDetailsDomain(currentTime, _pubtUiState.value.editMode, 0)
                    triggerCopy(electricalInspection, isInternetAvailable)
                }
                else -> null
            }
        }
    }

    private suspend fun triggerCopy(inspection: InspectionWithDetailsDomain, isInternetAvailable: Boolean) {
        val id = saveReport(inspection)

        if (id == null) return

        if (isInternetAvailable) {
            val cloudInspection = inspection.copy(inspection = inspection.inspection.copy(id = id))
            createReport(cloudInspection)
        } else {
            _pubtUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        }
    }

    fun onSaveClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.General_PUBT -> {
                    val electricalInspection = _generalUiState.value.toInspectionWithDetailsDomain(currentTime, _pubtUiState.value.editMode, currentReportId)
                    triggerSaving(electricalInspection, isInternetAvailable)
                }
                else -> {}
            }
        }
    }

    private suspend fun triggerSaving(inspection: InspectionWithDetailsDomain, isInternetAvailable: Boolean) {
        val isEditMode = _pubtUiState.value.editMode

        val id = saveReport(inspection)

        if (id == null) {
            return
        }

        if (isInternetAvailable) {
            val cloudInspection = inspection.copy(inspection = inspection.inspection.copy(id = id))
            if (isEditMode) {
                if (isSynced) updateReport(cloudInspection)
            } else {
                createReport(cloudInspection)
            }
        } else {
            _pubtUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        }
    }

    suspend fun saveReport(inspection: InspectionWithDetailsDomain): Long? {
        try {
            val id = reportUseCase.saveReport(inspection)
            return id
        } catch(_: SQLiteConstraintException) {
            _pubtUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
            return null
        } catch (_: Exception) {
            _pubtUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
            return null
        }
    }

    private suspend fun createReport(inspection: InspectionWithDetailsDomain) {
        try {
            Log.d("PUBTViewModel", "Creating report")
            reportUseCase.createReport(inspection).collect { result ->
                _pubtUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _pubtUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    private suspend fun updateReport(inspection: InspectionWithDetailsDomain) {
        try {
            reportUseCase.updateReport(inspection).collect { result ->
                _pubtUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _pubtUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    fun onUpdatePUBTState(updater: (PUBTUiState) -> PUBTUiState) {
        _pubtUiState.update(updater)
    }

    fun onGeneralReportChange(newReport: GeneralInspectionReport) {
        _generalUiState.update { it.copy(inspectionReport = newReport) }
    }

    fun updateMeasurementResultItem(item: GeneralMeasurementResultItem, type: MeasurementResultType) {
        val currentReport = _generalUiState.value.inspectionReport
        val currentInspection = currentReport.inspectionAndMeasurement

        val updatedInspection = when (type) {
            MeasurementResultType.TOP_HEAD -> currentInspection.copy(measurementResultsTopHead = item)
            MeasurementResultType.SHELL -> currentInspection.copy(measurementResultsShell = item)
            MeasurementResultType.BUTTON_HEAD -> currentInspection.copy(measurementResultsButtonHead = item)
        }

        onGeneralReportChange(currentReport.copy(inspectionAndMeasurement = updatedInspection))
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
                    // Store the report ID for editing
                    currentReportId = reportId
                    isSynced = inspection.inspection.isSynced
                    
                    // Extract the equipment type from the loaded inspection
                    val equipmentType = inspection.inspection.subInspectionType

                    when (equipmentType) {
                        SubInspectionType.General_PUBT -> _generalUiState.update { inspection.toGeneralUiState() }
                        else -> {}
                    }

                    _pubtUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Success("Data laporan berhasil dimuat untuk diedit"),
                            loadedEquipmentType = equipmentType
                        )
                    }
                } else {
                    _pubtUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _pubtUiState.update { 
                    it.copy(
                        isLoading = false,
                        editLoadResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }
}