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

    fun getInspectionDetail(id: Long) {
        viewModelScope.launch {
            onUpdateState { it.copy(isLoading = true) }
            val data = reportUseCase.getInspection(id)?.toElevatorBAPReport() ?: ElevatorBAPReport()
            onUpdateElevatorBAPState(data)
            onUpdateState { it.copy(isLoading = false) }
        }
    }

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Elevator -> {
                    val electricalInspection = _elevatorBAPUiState.value.elevatorBAPReport.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(electricalInspection)
                        _uiState.update { it.copy(elevatorResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(_: SQLiteConstraintException) {
                        _uiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (_: Exception) {
                        _uiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }
                SubInspectionType.Escalator -> TODO()
                SubInspectionType.Forklift -> TODO()
                SubInspectionType.Mobile_Crane -> TODO()
                SubInspectionType.Overhead_Crane -> TODO()
                SubInspectionType.Gantry_Crane -> TODO()
                SubInspectionType.Gondola -> TODO()
                SubInspectionType.Electrical -> TODO()
                SubInspectionType.Lightning_Conductor -> TODO()
                SubInspectionType.General_PUBT -> TODO()
                SubInspectionType.Fire_Protection -> TODO()
                SubInspectionType.Motor_Diesel -> TODO()
                SubInspectionType.Machine -> TODO()
            }
        }
    }

    fun onUpdateElevatorBAPState(newData: ElevatorBAPReport) {
        _elevatorBAPUiState.update { it.copy(elevatorBAPReport = newData) }
    }

    fun onUpdateState(updater: (BAPCreationUiState) -> BAPCreationUiState) {
        _uiState.update(updater)
    }
}