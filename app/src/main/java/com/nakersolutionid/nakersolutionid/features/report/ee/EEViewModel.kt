package com.nakersolutionid.nakersolutionid.features.report.ee

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.ElevatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.toElevatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorGeneralData
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.toEskalatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Dummy
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EEViewModel(
    private val reportUseCase: ReportUseCase,
    private val syncManager: SyncManager
) : ViewModel() {
    private val _eeUiState = MutableStateFlow(EEUiState())
    val eeUiState: StateFlow<EEUiState> = _eeUiState.asStateFlow()

    private val _elevatorUiState = MutableStateFlow(Dummy.getDummyElevatorUiState())
    val elevatorUiState: StateFlow<ElevatorUiState> = _elevatorUiState.asStateFlow()

    private val _eskalatorUiState = MutableStateFlow(Dummy.getDummyEskalatorUiState())
    val eskalatorUiState: StateFlow<EskalatorUiState> = _eskalatorUiState.asStateFlow()

    // Track current report ID for edit mode
    private var currentReportId: Long? = null
    private var isSynced = false

    fun onSaveClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Elevator -> {
                    val elevatorInspection = _elevatorUiState.value.toInspectionWithDetailsDomain(currentTime, _eeUiState.value.editMode, currentReportId)
                    triggerSaving(elevatorInspection, isInternetAvailable)
                }
                SubInspectionType.Escalator -> {
                    val escalatorInspection = _eskalatorUiState.value.toInspectionWithDetailsDomain(currentTime, _eeUiState.value.editMode, currentReportId)
                    triggerSaving(escalatorInspection, isInternetAvailable)
                }
                else -> null
            }
        }
    }

    private suspend fun triggerSaving(inspection: InspectionWithDetailsDomain, isInternetAvailable: Boolean) {
        val isEditMode = _eeUiState.value.editMode
        if (isInternetAvailable) {
            if (isEditMode) {
                if (isSynced) updateReport(inspection) else saveReport(inspection)
            } else {
                createReport(inspection)
            }
        } else {
            saveReport(inspection)
        }
    }

    suspend fun saveReport(inspection: InspectionWithDetailsDomain) {
        try {
            reportUseCase.saveReport(inspection)
            _eeUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        } catch(_: SQLiteConstraintException) {
            _eeUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        } catch (_: Exception) {
            _eeUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    private suspend fun createReport(inspection: InspectionWithDetailsDomain) {
        try {
            Log.d("PUBTViewModel", "Creating report")
            reportUseCase.createReport(inspection).collect { result ->
                _eeUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _eeUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    private suspend fun updateReport(inspection: InspectionWithDetailsDomain) {
        try {
            reportUseCase.updateReport(inspection).collect { result ->
                _eeUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _eeUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    /**
     * A generic handler to update the entire Escalator data state.
     * The UI layer creates a new copy of the data with the modified field,
     * and this function updates the StateFlow to trigger a UI recomposition.
     *
     * @param newEskalatorData The new, updated state of the escalator data.
     */
    fun onDataChange(newEskalatorData: EskalatorGeneralData) {
        _eskalatorUiState.update { it.copy(eskalatorData = newEskalatorData) }
    }

    /**
     * A generic handler to update the entire Elevator data state.
     * The UI layer creates a new copy of the data with the modified field,
     * and this function updates the StateFlow to trigger a UI recomposition.
     *
     * @param newElevatorData The new, updated state of the elevator data.
     */
    fun onDataChange(newElevatorData: ElevatorUiState) {
        _elevatorUiState.update { newElevatorData }
    }

    fun onUpdateState(updater: (EEUiState) -> EEUiState) {

        _eeUiState.update(updater)
    }

    /**
     * Load an existing report for editing.
     * @param reportId The ID of the report to load for editing
     */
    fun loadReportForEdit(reportId: Long) {
        viewModelScope.launch {
            try {
                _eeUiState.update { it.copy(isLoading = true) }
                val inspection = reportUseCase.getInspection(reportId)
                
                if (inspection != null) {
                    // Store the report ID for editing
                    currentReportId = reportId
                    isSynced = inspection.inspection.isSynced

                    // Extract the equipment type from the loaded inspection
                    val equipmentType = inspection.inspection.subInspectionType

                    when (equipmentType) {
                        SubInspectionType.Elevator -> _elevatorUiState.update { inspection.toElevatorUiState() }
                        SubInspectionType.Escalator -> _eskalatorUiState.update { inspection.toEskalatorUiState() }
                        else -> {}
                    }

                    _eeUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Success("Data laporan berhasil dimuat untuk diedit"),
                            loadedEquipmentType = equipmentType
                        )
                    }
                } else {
                    _eeUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _eeUiState.update { 
                    it.copy(
                        isLoading = false,
                        editLoadResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }
}