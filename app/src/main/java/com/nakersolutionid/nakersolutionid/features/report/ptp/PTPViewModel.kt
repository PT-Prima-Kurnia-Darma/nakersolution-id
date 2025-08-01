package com.nakersolutionid.nakersolutionid.features.report.ptp

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
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.toMachineUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.toDieselMotorUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import com.nakersolutionid.nakersolutionid.workers.SyncManager
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PTPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ptpUiState = MutableStateFlow(PTPUiState())
    val ptpUiState: StateFlow<PTPUiState> = _ptpUiState.asStateFlow()

//    private val _machineUiState = MutableStateFlow(ProductionMachineUiState.createDummyProductionMachineUiState())
    private val _machineUiState = MutableStateFlow(ProductionMachineUiState())
    val machineUiState: StateFlow<ProductionMachineUiState> = _machineUiState.asStateFlow()

//    private val _motorDieselUiState = MutableStateFlow(DieselMotorUiState.createDummyDieselMotorUiState())
    private val _motorDieselUiState = MutableStateFlow(DieselMotorUiState())
    val motorDieselUiState: StateFlow<DieselMotorUiState> = _motorDieselUiState.asStateFlow()

    // Store the current report ID for editing
    private var currentReportId: Long? = null
    private var isSynced = false

    fun onGetMLResult(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Machine -> {
                    val inspection = _machineUiState.value.toInspectionWithDetailsDomain(currentTime, _ptpUiState.value.editMode, currentReportId)
                    collectMlResult(inspection)
                }
                SubInspectionType.Motor_Diesel -> {
                    val inspection = _motorDieselUiState.value.toInspectionWithDetailsDomain(currentTime, _ptpUiState.value.editMode, currentReportId)
                    collectMlResult(inspection)
                }
                else -> {}
            }
        }
    }

    private suspend fun collectMlResult(inspection: InspectionWithDetailsDomain) {
        reportUseCase.getMLResult(inspection).collect { data ->
            when (data) {
                is Resource.Error -> onUpdatePTPState { it.copy(mlResult = data.message, mlLoading = false) }
                is Resource.Loading -> onUpdatePTPState { it.copy(mlLoading = true) }
                is Resource.Success -> {
                    val conclusion = data.data?.conclusion ?: ""
                    val recommendation = data.data?.recommendation?.toImmutableList() ?: persistentListOf()
                    when (inspection.inspection.subInspectionType) {
                        SubInspectionType.Machine -> {
                            val report = _machineUiState.value.inspectionReport
                            val updatedConclusion = report.conclusion.copy(
                                summary = persistentListOf(conclusion),
                                requirements = recommendation
                            )
                            onMachineReportChange(report.copy(conclusion = updatedConclusion))
                        }
                        SubInspectionType.Motor_Diesel -> {
                            val report = _motorDieselUiState.value.inspectionReport
                            val updatedConclusion = report.conclusion.copy(
                                summary = persistentListOf(conclusion),
                                requirements = recommendation
                            )
                            onMotorDieselReportChange(report.copy(conclusion = updatedConclusion))
                        }
                        else -> {}
                    }
                    onUpdatePTPState { it.copy(mlLoading = false) }
                }
            }
        }
    }

    fun onCopyClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Machine -> {
                    val inspection = _machineUiState.value.toInspectionWithDetailsDomain(currentTime, _ptpUiState.value.editMode, 0)
                    triggerCopy(inspection, isInternetAvailable)
                }

                SubInspectionType.Motor_Diesel -> {
                    val inspection = _motorDieselUiState.value.toInspectionWithDetailsDomain(currentTime, _ptpUiState.value.editMode, 0)
                    triggerCopy(inspection, isInternetAvailable)
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
            _ptpUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        }
    }

    fun onSaveClick(selectedIndex: SubInspectionType, isInternetAvailable: Boolean) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Machine -> {
                    val inspection = _machineUiState.value.toInspectionWithDetailsDomain(currentTime, _ptpUiState.value.editMode, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }

                SubInspectionType.Motor_Diesel -> {
                    val inspection = _motorDieselUiState.value.toInspectionWithDetailsDomain(currentTime, _ptpUiState.value.editMode, currentReportId)
                    triggerSaving(inspection, isInternetAvailable)
                }
                else -> {}
            }
        }
    }

    private suspend fun triggerSaving(inspection: InspectionWithDetailsDomain, isInternetAvailable: Boolean) {
        val isEditMode = _ptpUiState.value.editMode

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
            _ptpUiState.update { it.copy(result = Resource.Success("Laporan berhasil disimpan")) }
        }
    }

    suspend fun saveReport(inspection: InspectionWithDetailsDomain): Long? {
        try {
            val id = reportUseCase.saveReport(inspection)
            return id
        } catch(_: SQLiteConstraintException) {
            _ptpUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
            return null
        } catch (_: Exception) {
            _ptpUiState.update { it.copy(result = Resource.Error("Laporan gagal disimpan")) }
            return null
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
}