package com.nakersolutionid.nakersolutionid.features.bap

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.elevator.toElevatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.elevator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.escalator.EscalatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.escalator.EscalatorBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.escalator.toEscalatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.escalator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.forklift.ForkliftBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.forklift.ForkliftBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.forklift.toForkliftBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.forklift.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.GantryCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.GantryCraneBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.toGantryCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gantrycrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.gondola.GondolaBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gondola.GondolaBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.gondola.toGondolaBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.gondola.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.MobileCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.MobileCraneBAPUiState
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.toMobileCraneBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.mobilecrane.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BAPCreationViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(BAPCreationUiState())
    val uiState: StateFlow<BAPCreationUiState> = _uiState.asStateFlow()

    private val _elevatorBAPUiState = MutableStateFlow(ElevatorBAPUiState())
    val elevatorBAPUiState: StateFlow<ElevatorBAPUiState> = _elevatorBAPUiState.asStateFlow()

    private val _escalatorBAPUiState = MutableStateFlow(EscalatorBAPUiState())
    val escalatorBAPUiState: StateFlow<EscalatorBAPUiState> = _escalatorBAPUiState.asStateFlow()

    private val _forkliftBAPUiState = MutableStateFlow(ForkliftBAPUiState())
    val forkliftBAPUiState: StateFlow<ForkliftBAPUiState> = _forkliftBAPUiState.asStateFlow()

    private val _gantryCraneBAPUiState = MutableStateFlow(GantryCraneBAPUiState())
    val gantryCraneBAPUiState: StateFlow<GantryCraneBAPUiState> = _gantryCraneBAPUiState.asStateFlow()

    private val _gondolaBAPUiState = MutableStateFlow(GondolaBAPUiState())
    val gondolaBAPUiState: StateFlow<GondolaBAPUiState> = _gondolaBAPUiState.asStateFlow()

    private val _mobileCraneBAPUiState = MutableStateFlow(MobileCraneBAPUiState())
    val mobileCraneBAPUiState: StateFlow<MobileCraneBAPUiState> = _mobileCraneBAPUiState.asStateFlow()

    fun getInspectionDetail(id: Long) {
        viewModelScope.launch {
            onUpdateState { it.copy(isLoading = true) }
            val inspection = reportUseCase.getInspection(id)
            if (inspection != null) {
                // Load appropriate data based on subInspectionType
                when (inspection.inspection.subInspectionType) {
                    SubInspectionType.Elevator -> {
                        val data = inspection.toElevatorBAPReport()
                        onUpdateElevatorBAPState(data)
                    }
                    SubInspectionType.Escalator -> {
                        val data = inspection.toEscalatorBAPReport()
                        onUpdateEscalatorBAPState(data)
                    }
                    SubInspectionType.Forklift -> {
                        val data = inspection.toForkliftBAPReport()
                        onUpdateForkliftBAPState(data)
                    }
                    SubInspectionType.Gantry_Crane -> {
                        val data = inspection.toGantryCraneBAPReport()
                        onUpdateGantryCraneBAPState(data)
                    }
                    SubInspectionType.Gondola -> {
                        val data = inspection.toGondolaBAPReport()
                        onUpdateGondolaBAPState(data)
                    }
                    SubInspectionType.Mobile_Crane -> {
                        val data = inspection.toMobileCraneBAPReport()
                        onUpdateMobileCraneBAPState(data)
                    }
                    else -> {
                        // Default to elevator for unsupported types
                        val data = inspection.toElevatorBAPReport()
                        onUpdateElevatorBAPState(data)
                    }
                }
            }
            onUpdateState { it.copy(isLoading = false) }
        }
    }

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Elevator -> {
                    val inspection = _elevatorBAPUiState.value.elevatorBAPReport.toInspectionWithDetailsDomain(currentTime)
                    saveReport(inspection)
                }
                SubInspectionType.Escalator -> {
                    val inspection = _escalatorBAPUiState.value.escalatorBAPReport.toInspectionWithDetailsDomain(currentTime)
                    saveReport(inspection)
                }
                SubInspectionType.Forklift -> {
                    val inspection = _forkliftBAPUiState.value.forkliftBAPReport.toInspectionWithDetailsDomain(currentTime)
                    saveReport(inspection)
                }
                SubInspectionType.Gantry_Crane -> {
                    val inspection = _gantryCraneBAPUiState.value.gantryCraneBAPReport.toInspectionWithDetailsDomain(currentTime)
                    saveReport(inspection)
                }
                SubInspectionType.Gondola -> {
                    val inspection = _gondolaBAPUiState.value.gondolaBAPReport.toInspectionWithDetailsDomain(currentTime)
                    saveReport(inspection)
                }
                SubInspectionType.Mobile_Crane -> {
                    val inspection = _mobileCraneBAPUiState.value.mobileCraneBAPReport.toInspectionWithDetailsDomain(currentTime)
                    saveReport(inspection)
                }
                SubInspectionType.Overhead_Crane -> TODO()
                SubInspectionType.Electrical -> TODO()
                SubInspectionType.Lightning_Conductor -> TODO()
                SubInspectionType.General_PUBT -> TODO()
                SubInspectionType.Fire_Protection -> TODO()
                SubInspectionType.Motor_Diesel -> TODO()
                SubInspectionType.Machine -> TODO()
            }
        }
    }

    private suspend fun saveReport(inspection: com.nakersolutionid.nakersolutionid.domain.model.InspectionWithDetailsDomain) {
        try {
            reportUseCase.saveReport(inspection)
            _uiState.update { it.copy(elevatorResult = Resource.Success("Laporan berhasil disimpan")) }
        } catch(_: SQLiteConstraintException) {
            _uiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
        } catch (_: Exception) {
            _uiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
        }
    }

    fun onUpdateElevatorBAPState(newData: ElevatorBAPReport) {
        _elevatorBAPUiState.update { it.copy(elevatorBAPReport = newData) }
    }

    fun onUpdateEscalatorBAPState(newData: EscalatorBAPReport) {
        _escalatorBAPUiState.update { it.copy(escalatorBAPReport = newData) }
    }

    fun onUpdateForkliftBAPState(newData: ForkliftBAPReport) {
        _forkliftBAPUiState.update { it.copy(forkliftBAPReport = newData) }
    }

    fun onUpdateGantryCraneBAPState(newData: GantryCraneBAPReport) {
        _gantryCraneBAPUiState.update { it.copy(gantryCraneBAPReport = newData) }
    }

    fun onUpdateGondolaBAPState(newData: GondolaBAPReport) {
        _gondolaBAPUiState.update { it.copy(gondolaBAPReport = newData) }
    }

    fun onUpdateMobileCraneBAPState(newData: MobileCraneBAPReport) {
        _mobileCraneBAPUiState.update { it.copy(mobileCraneBAPReport = newData) }
    }

    fun onUpdateState(updater: (BAPCreationUiState) -> BAPCreationUiState) {
        _uiState.update(updater)
    }
}