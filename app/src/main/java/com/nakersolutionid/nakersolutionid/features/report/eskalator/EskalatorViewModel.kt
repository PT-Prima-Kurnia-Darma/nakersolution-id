package com.nakersolutionid.nakersolutionid.features.report.eskalator

import androidx.lifecycle.ViewModel
import com.nakersolutionid.nakersolutionid.domain.usecase.ReportUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EskalatorViewModel(private val reportUseCase: ReportUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(EskalatorUiState())
    val uiState = _uiState.asStateFlow()

    /**
     * A generic handler to update the entire Eskalator data state.
     * The UI layer creates a new copy of the data with the modified field,
     * and this function updates the StateFlow to trigger a UI recomposition.
     *
     * @param newEskalatorData The new, updated state of the escalator data.
     */
    fun onDataChange(newEskalatorData: EskalatorGeneralData) {
        _uiState.update { it.copy(eskalatorData = newEskalatorData) }
    }
}