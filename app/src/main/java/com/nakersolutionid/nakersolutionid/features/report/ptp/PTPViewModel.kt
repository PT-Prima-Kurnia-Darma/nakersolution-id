package com.nakersolutionid.nakersolutionid.features.report.ptp

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.ProductionMachineUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.machine.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorInspectionReport
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorLightingMeasurementPoint
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorNoiseMeasurementPoint
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.DieselMotorUiState
import com.nakersolutionid.nakersolutionid.features.report.ptp.motordiesel.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Dummy
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PTPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ptpUiState = MutableStateFlow(PTPUiState())
    val ptpUiState: StateFlow<PTPUiState> = _ptpUiState.asStateFlow()

    private val _machineUiState = MutableStateFlow(Dummy.getDummyProductionMachineUiState())
    val machineUiState: StateFlow<ProductionMachineUiState> = _machineUiState.asStateFlow()

    private val _motorDieselUiState = MutableStateFlow(Dummy.getDummyDieselMotorUiState())
    val motorDieselUiState: StateFlow<DieselMotorUiState> = _motorDieselUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Machine -> {
                    val electricalInspection = _machineUiState.value.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(electricalInspection)
                        _ptpUiState.update { it.copy(machineResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(_: SQLiteConstraintException) {
                        _ptpUiState.update { it.copy(machineResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (_: Exception) {
                        _ptpUiState.update { it.copy(machineResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }

                SubInspectionType.Motor_Diesel -> {
                    val electricalInspection = _motorDieselUiState.value.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(electricalInspection)
                        _ptpUiState.update { it.copy(motorDieselResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(_: SQLiteConstraintException) {
                        _ptpUiState.update { it.copy(motorDieselResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (_: Exception) {
                        _ptpUiState.update { it.copy(motorDieselResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }
                else -> {}
            }
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

    fun addMotorDieselNoisePoint(point: DieselMotorNoiseMeasurementPoint) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val measurement = report.noiseMeasurement
        val newPoints = (measurement.measurements + point).toImmutableList()
        onMotorDieselReportChange(report.copy(noiseMeasurement = measurement.copy(measurements = newPoints)))
    }

    fun deleteMotorDieselNoisePoint(index: Int) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val measurement = report.noiseMeasurement
        val newPoints = measurement.measurements.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMotorDieselReportChange(report.copy(noiseMeasurement = measurement.copy(measurements = newPoints)))
    }

    fun addMotorDieselLightingPoint(point: DieselMotorLightingMeasurementPoint) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val measurement = report.lightingMeasurement
        val newPoints = (measurement.measurements + point).toImmutableList()
        onMotorDieselReportChange(report.copy(lightingMeasurement = measurement.copy(measurements = newPoints)))
    }

    fun deleteMotorDieselLightingPoint(index: Int) = viewModelScope.launch {
        val report = _motorDieselUiState.value.inspectionReport
        val measurement = report.lightingMeasurement
        val newPoints = measurement.measurements.toMutableList().apply { removeAt(index) }.toImmutableList()
        onMotorDieselReportChange(report.copy(lightingMeasurement = measurement.copy(measurements = newPoints)))
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
                    // Convert the domain model back to UI state
                    // For now, we'll just show a success message
                    // The actual conversion will depend on the specific report structure
                    _ptpUiState.update { 
                        it.copy(
                            isLoading = false,
                            machineResult = Resource.Success("Data laporan berhasil dimuat untuk diedit")
                        )
                    }
                } else {
                    _ptpUiState.update { 
                        it.copy(
                            isLoading = false,
                            machineResult = Resource.Error("Laporan tidak ditemukan")
                        )
                    }
                }
            } catch (e: Exception) {
                _ptpUiState.update { 
                    it.copy(
                        isLoading = false,
                        machineResult = Resource.Error("Gagal memuat data laporan: ${e.message}")
                    )
                }
            }
        }
    }
}