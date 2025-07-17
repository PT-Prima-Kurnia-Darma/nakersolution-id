package com.nakersolutionid.nakersolutionid.features.bap

import androidx.lifecycle.ViewModel
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPReport
import com.nakersolutionid.nakersolutionid.features.bap.elevator.ElevatorBAPUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BAPCreationViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(BAPCreationUiState())
    val uiState: StateFlow<BAPCreationUiState> = _uiState.asStateFlow()

    private val _elevatorBAPUiState = MutableStateFlow(ElevatorBAPUiState())
    val elevatorBAPUiState: StateFlow<ElevatorBAPUiState> = _elevatorBAPUiState.asStateFlow()

    /*fun getInspectionDetail(id: Long) {
        viewModelScope.launch {
            onUpdateState { it.copy(isLoading = true) }
            val data = reportUseCase.getInspection(id)
            onUpdateState { it.copy(isLoading = false) }
        }
    }*/

    /*fun onSaveClick() {

    }*/

    fun onUpdateElevatorBAPState(newData: ElevatorBAPReport) {
        _elevatorBAPUiState.update { it.copy(elevatorBAPReport = newData) }
    }

    fun onUpdateState(updater: (BAPCreationUiState) -> BAPCreationUiState) {
        _uiState.update(updater)
    }
}