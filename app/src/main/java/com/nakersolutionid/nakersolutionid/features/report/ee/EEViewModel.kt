package com.nakersolutionid.nakersolutionid.features.report.ee

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.ElevatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorGeneralData
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.toInspectionWithDetailsDomain
import com.nakersolutionid.nakersolutionid.utils.Dummy
import com.nakersolutionid.nakersolutionid.utils.Utils.getCurrentTime
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EEViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _eeUiState = MutableStateFlow(EEUiState())
    val eeUiState: StateFlow<EEUiState> = _eeUiState.asStateFlow()

    private val _elevatorUiState = MutableStateFlow(Dummy.getDummyElevatorUiState())
    val elevatorUiState: StateFlow<ElevatorUiState> = _elevatorUiState.asStateFlow()

    private val _eskalatorUiState = MutableStateFlow(Dummy.getDummyEskalatorUiState())
    val eskalatorUiState: StateFlow<EskalatorUiState> = _eskalatorUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            val currentTime = getCurrentTime()
            when (selectedIndex) {
                SubInspectionType.Elevator -> {
                    val elevatorInspection = _elevatorUiState.value.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(elevatorInspection)
                        _eeUiState.update { it.copy(elevatorResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(e: SQLiteConstraintException) {
                        _eeUiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (e: Exception) {
                        _eeUiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }
                SubInspectionType.Escalator -> {
                    val escalatorInspection = _eskalatorUiState.value.toInspectionWithDetailsDomain(currentTime)
                    try {
                        reportUseCase.saveReport(escalatorInspection)
                        _eeUiState.update { it.copy(elevatorResult = Resource.Success("Laporan berhasil disimpan")) }
                    } catch(e: SQLiteConstraintException) {
                        _eeUiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
                    } catch (e: Exception) {
                        _eeUiState.update { it.copy(elevatorResult = Resource.Error("Laporan gagal disimpan")) }
                    }
                }
                else -> null
            }
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
}