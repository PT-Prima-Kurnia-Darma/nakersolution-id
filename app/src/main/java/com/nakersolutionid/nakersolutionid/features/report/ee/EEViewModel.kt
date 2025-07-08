package com.nakersolutionid.nakersolutionid.features.report.ee

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.Resource
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.report.ee.elevator.ElevatorUiState
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorGeneralData
import com.nakersolutionid.nakersolutionid.features.report.ee.eskalator.EskalatorUiState
import com.nakersolutionid.nakersolutionid.utils.toDomain
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EEViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _eeUiState = MutableStateFlow(EEUiState())
    val eeUiState: StateFlow<EEUiState> = _eeUiState.asStateFlow()

    private val _elevatorUiState = MutableStateFlow(ElevatorUiState())
    val elevatorUiState: StateFlow<ElevatorUiState> = _elevatorUiState.asStateFlow()

    private val _eskalatorUiState = MutableStateFlow(EskalatorUiState())
    val eskalatorUiState: StateFlow<EskalatorUiState> = _eskalatorUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Elevator -> {
                    reportUseCase.sendReport(_elevatorUiState.value.toDomain()).collect { result ->
                        _eeUiState.update { it.copy(elevatorResult = result) }
                    }
                }
                SubInspectionType.Eskalator -> {
                    _eeUiState.update { it.copy(eskalatorResult = Resource.Error("Not Implemented Yet")) }
                }
                else -> null
            }
        }
    }

    /**
     * A generic handler to update the entire Eskalator data state.
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