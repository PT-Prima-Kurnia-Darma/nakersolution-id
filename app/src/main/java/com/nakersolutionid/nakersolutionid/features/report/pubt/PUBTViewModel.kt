package com.nakersolutionid.nakersolutionid.features.report.pubt

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PUBTViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _pubtUiState = MutableStateFlow(PUBTUiState())
    val pubtUiState: StateFlow<PUBTUiState> = _pubtUiState.asStateFlow()

    // private val _generalUiState = MutableStateFlow()
    // val generalUiState: StateFlow<> = _generalUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.General_PUBT -> {
                    // TODO: Implement save logic for Machine report using _machineUiState.value
                }
                else -> {}
            }
        }
    }


}