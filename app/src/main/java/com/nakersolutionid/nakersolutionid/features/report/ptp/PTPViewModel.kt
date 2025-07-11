package com.nakersolutionid.nakersolutionid.features.report.ptp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nakersolutionid.nakersolutionid.data.local.utils.SubInspectionType
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PTPViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _ptpUiState = MutableStateFlow(PTPUiState())
    val ptpUiState: StateFlow<PTPUiState> = _ptpUiState.asStateFlow()

    fun onSaveClick(selectedIndex: SubInspectionType) {
        viewModelScope.launch {
            when (selectedIndex) {
                SubInspectionType.Machine -> {
                    // TODO: Implement save logic for Electrical Installation report using _electricalUiState.value
                }

                SubInspectionType.Motor_Diesel -> {
                    // TODO: Implement save logic for Lightning Protection report using _lightningUiState.value
                }
                else -> {}
            }
        }
    }
}