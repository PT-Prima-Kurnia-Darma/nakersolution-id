package com.nakersolutionid.nakersolutionid.features.report.paa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PAAViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _paaUiState = MutableStateFlow(PAAUiState())
    val paaUiState: StateFlow<PAAUiState> = _paaUiState.asStateFlow()

    // private val _forkliftUiState = MutableStateFlow()
    // val forkliftUiState: StateFlow<PAAUiState> = _forkliftUiState.asStateFlow()

    // private val _gantryCraneUiState = MutableStateFlow()
    // val gantryCraneUiState: StateFlow<PAAUiState> = _gantryCraneUiState.asStateFlow()

    // private val _gondolaUiState = MutableStateFlow()
    // val gondolaUiState: StateFlow<PAAUiState> = _gondolaUiState.asStateFlow()

    // private val _mobileCraneUiState = MutableStateFlow()
    // val mobileCraneUiState: StateFlow<PAAUiState> = _mobileCraneUiState.asStateFlow()

    // private val _overheadCraneUiState = MutableStateFlow()
    // val overheadCraneUiState: StateFlow<PAAUiState> = _overheadCraneUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Forklift -> {}
                SubInspectionType.Bulldozer -> {}
                SubInspectionType.Excavator -> {}
                SubInspectionType.Wheel_Loader -> {}
                SubInspectionType.Farm_Tractor -> {}
                SubInspectionType.Motor_Grader -> {}
                SubInspectionType.Mobil_Crane -> {}
                SubInspectionType.Hoist_Crane -> {}
                SubInspectionType.Tower_Crane -> {}
                SubInspectionType.Truck_Crane -> {}
                SubInspectionType.Overhead_Crane -> {}
                SubInspectionType.Gantry_Crane -> {}
                SubInspectionType.Gondola -> {}
                SubInspectionType.Jib_Crane -> {}
                else -> null
            }
        }
    }

    fun onUpdateState(updater: (PAAUiState) -> PAAUiState) {
        _paaUiState.update(updater)
    }
}