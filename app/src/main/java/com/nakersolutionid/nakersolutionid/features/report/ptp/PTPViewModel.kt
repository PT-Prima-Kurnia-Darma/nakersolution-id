package com.nakersolutionid.nakersolutionid.features.report.ptp

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.toMachineUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.toDieselMotorUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Dummy
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PTPViewModel(
    private val reportUseCase: ReportUseCase,
    private val syncManager: SyncManager
) : ViewModel() {
    private val _ptpUiState = MutableStateFlow(PTPUiState())
    val ptpUiState: StateFlow<PTPUiState> = _ptpUiState.asStateFlow()

    private val _machineUiState = MutableStateFlow(Dummy.getDummyProductionMachineUiState())
    val machineUiState: StateFlow<ProductionMachineUiState> = _machineUiState.asStateFlow()

    private val _motorDieselUiState = MutableStateFlow(Dummy.getDummyDieselMotorUiState())
    val motorDieselUiState: StateFlow<DieselMotorUiState> = _motorDieselUiState.asStateFlow()

    // Store the current report ID for editing
    private var currentReportId: Long? = null
    private var isSynced = false

    fun onSaveClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Machine -> {
                    val inspection = _machineUiState.value.toInspectionWithDetailsDomain(currentTime, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }

                SubInspectionType.Motor_Diesel -> {
                    val inspection = _motorDieselUiState.value.toInspectionWithDetailsDomain(currentTime, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }
                else -> {}
            }
        }
    }

    private suspend fun triggerSaving(inspection: InspectionWithDetailsDomain, isInternetAvailable: Boolean) {
        val isEditMode = _ptpUiState.value.editMode
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
            _ptpUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        } catch(_: SQLiteConstraintException) {
            _ptpUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        } catch (_: Exception) {
            _ptpUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    private suspend fun createReport(inspection: InspectionWithDetailsDomain) {
        try {
            Log.d("PUBTViewModel", "Creating report")
            reportUseCase.createReport(inspection).collect { result ->
                _ptpUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _ptpUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    private suspend fun updateReport(inspection: InspectionWithDetailsDomain) {
        try {
            reportUseCase.updateReport(inspection).collect { result ->
                _ptpUiState.update { it.copy(result = result) }
            }
        } catch (_: Exception) {
            _ptpUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    fun onUpdatePTPState(updater: (PTPUiState) -> PTPUiState) {
        _ptpUiState.update(updater)
    }

    //region Machine Logic
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
    //endregion

    //region Motor Diesel Logic
    fun onMotorDieselReportChange(newReport: DieselMotorInspectionReport) {
        _motorDieselUiState.update { it.copy(inspectionReport = newReport) }
    }



    fun addMotorDieselConclusionSummary(item: String) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.summary + item).toImmutableList()
        onMotorDieselReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun removeMotorDieselConclusionSummary(index: Int) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.summary.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMotorDieselReportChange(report.copy(conclusion = conclusion.copy(summary = newItems)))
    }

    fun addMotorDieselConclusionRequirement(item: String) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = (conclusion.requirements + item).toImmutableList()
        onMotorDieselReportChange(report.copy(conclusion = conclusion.copy(requirements = newItems)))
    }

    fun removeMotorDieselConclusionRequirement(index: Int) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val conclusion = report.conclusion
        val newItems = conclusion.requirements.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMotorDieselReportChange(report.copy(conclusion = conclusion.copy(requirements = newItems)))
    }
    //endregion

    /**
     * Load an existing report for editing.
     * @param reportId The ID of the report to load for editing
     */
    fun loadReportForEdit(reportId: Long) {
        viewModelScope.launch {
            try {
                _ptpUiState.update { it.copy(isLoading = true) }
                val inspection = reportUseCase.getInspection(reportId)
                
                if (inspection != null) {
                    // Store the report ID for editing
                    currentReportId = reportId
                    isSynced = inspection.inspection.isSynced
                    
                    // Extract the equipment type from the loaded inspection
                    val equipmentType = inspection.inspection.subInspectionType

                    when (equipmentType) {
                        SubInspectionType.Machine -> _machineUiState.update { inspection.toMachineUiState() }
                        SubInspectionType.Motor_Diesel -> _motorDieselUiState.update { inspection.toDieselMotorUiState() }
                        else -> {}
                    }

                    _ptpUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Success("Data laporan berhasil dimuat untuk diedit"),
                            loadedEquipmentType = equipmentType
                        )
                    }
                } else {
                    _ptpUiState.update { 
                        it.copy(
                            isLoading = false,
                            editLoadResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _ptpUiState.update { 
                    it.copy(
                        isLoading = false,
                        editLoadResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }

    fun startSync() {
        if (_ptpUiState.value.loadedEquipmentType != null) {
            Log.d("PTPViewModel", "Starting sync update")
            syncManager.startSyncUpdate()
            _ptpUiState.update { it.copy(loadedEquipmentType = null) }
        } else {
            Log.d("PTPViewModel", "Starting sync")
            syncManager.startSync()
            _ptpUiState.update { it.copy(loadedEquipmentType = null) }
        }
    }
}