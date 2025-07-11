package com.nakersolutionid.nakersolutionid.features.report.ilpp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ILPPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ilppUiState = MutableStateFlow(ILPPUiState())
    val ilppUiState: StateFlow<ILPPUiState> = _ilppUiState.asStateFlow()

    // private val _electricUiState = MutableStateFlow(ILPPUiState())
    // val electricUiState: StateFlow<ILPPUiState> = _electricUiState.asStateFlow()

    // private val _lightningUiState = MutableStateFlow(ILPPUiState())
    // val lightningUiState: StateFlow<ILPPUiState> = _lightningUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Instalasi_Listrik -> {
                    // TODO: Implement save logic for Forklift report
                }

                SubInspectionType.Instalasi_Penyalur_Petir -> {
                    // TODO: Implement save logic for Mobile Crane report
                }
                else -> {}
            }
        }
    }


}